package com.zt.web.taglib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zt.io.FileOperation;
import com.zt.util.StringUtil;


/**
 * ��ҳ��ǩ
 * @author zt
 * @since 2010-3-6
 */
public class PaperTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 772405902899222598L;
	
	private Integer pageIndex=0; //��ǰҳ������
	
	private Integer pageSize=10; //��ǰҳ����ʾ�����еĴ�С �������
	
	private Integer pageCount; //����������ռ�ݵ���ҳ��
	
	private String requestUrl; //��ǰҳ���Url
	
	private String formName="paper_form"; //��ǰ��ҳ�ؼ����ڵı�����
	
	private Integer recordCount; //����������
	
	private Boolean alwaysShow=false; //������ʾ
	
	private String cssClass; //��ҳ�ؼ���div��class����
	
	private Boolean postSubmit=false;
	
	private Boolean showInputBox=false; //�Ƿ���ʾ�����ı���
	
	private String submitText="ת��"; //�ύ��ť�ı�
	
	private String pageInfo; //��ǰ��ҳ��Ϣ
	
	private Boolean showPapeInfoLeft=false; //��paperInfo��ʾ����߻����ұ�
	
	private Boolean showPageItems =false; //�Ƿ���ʾ������
	
	private Boolean showPageInfo=false;
	
	private String firstText;
	
	private String previousText;
	
	private String nextText;
	
	private String lastText;
	
	private static String script=null;

	private boolean isFirst; //��ǰҳ�Ƿ��ǵ�һҳ
	
	private boolean isLast; //��ǰҳ�Ƿ������һҳ
	
	private boolean hasParams=false; //�Ƿ���Url����������
	
	
	private HttpServletRequest request;
	private  String style=" style=\" margin:0px 3px;\"";
	private  String redStyle=" style=\" color:red;margin:0px 3px;\"";
	
	
	private void init()
	{
		
		request= (HttpServletRequest) pageContext.getRequest();
		getRequestUrl();
		getPageIndex();
		getPageSize();
		getPageCount();
		
		isFirst=pageIndex==0;
		isLast=(pageIndex==pageCount-1);
		
		request.setAttribute("pageIndex", pageIndex);
		request.setAttribute("pageCount", pageCount);
	}
	
	@Override
	public int doStartTag() throws JspException {
		init();
		if (recordCount>0) {
			showPaperTag();
		}
		
		return super.doStartTag();
	}
	
	private void showPaperTag()
	{
		if (!alwaysShow&&pageCount==1) {
			return;
		}
		
//		System.out.println(request.getRequestURL().toString());
//		System.out.println("currentUrl:"+currentUrl);
//		System.out.println("pageindex:"+pageIndex);
//		System.out.println("pageCount:"+pageCount);
//		System.out.println("pageInfo"+pageInfo);
		
		StringBuilder sbOut = new StringBuilder();
		
		
		appendScript(sbOut); //׷��script�ű�
		sbOut.append("<form name=\"paper_form\" method=\"post\" action=\""+requestUrl+"\" >");
		sbOut.append("<div class=\"").append(cssClass==null?"":cssClass).append("\" >");
		
		if (showPapeInfoLeft) {
			appendPageInfo(sbOut);
			appendPaper(sbOut);
			appendInputBox(sbOut);
		}else {
			appendPaper(sbOut);
			appendInputBox(sbOut);
			appendPageInfo(sbOut);
		}
		
		if (postSubmit) {
			appendHidden(sbOut);
		}
		
		sbOut.append("</div>");
		sbOut.append("</form>");
		try {
			pageContext.getOut().write(sbOut.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendHidden(StringBuilder sbOut) {
		if (request.getParameterMap().size()>0) {
			Map<String,Object> map= request.getParameterMap();
			
			hasParams=false;
			
			for (Object key : map.keySet()) {
				if ("p".equals(key)) {
					continue;
				}
				if (StringUtil.IsNullOrEmpty(request.getParameter(key.toString()))) {
					continue;
				}
				System.out.println(request.getParameter(key.toString()));
				appendHiddenField(sbOut, key.toString(),request.getParameter(key.toString()));
			}
			
		}
		
	}

	private void appendScript(StringBuilder sbOut) {
		if (postSubmit) {
			sbOut.append(getScript());
		}
	}

	private void appendPaper(StringBuilder sbOut) {
		
		String disabled="disabled=\"true\"";
		String firstUrl="href=\""+requestUrl+"\"";
		String previousUrl="href=\""+requestUrl+(hasParams?"&":"?")+"p="+(pageIndex-1)+"\"";
		String nextUrl="href=\""+requestUrl+(hasParams?"&":"?")+"p="+(pageIndex+1)+"\"";
		String lastUrl="href=\""+requestUrl+(hasParams?"&":"?")+"p="+(pageCount-1)+"\"";
		
		sbOut.append("<span class=\"paper_core\">");
		if (postSubmit) { //���ΪPost�ύ
			
			
			sbOut.append("<a ").append(isFirst?disabled:("href=\"javascript:paper_submit(0,'"+formName+"');  \"")).append(style).append(" >").append(getFirstText()).append("</a>");
			sbOut.append("<a ").append(isFirst?disabled:("href=\"javascript:paper_submit("+(pageIndex-1)+",'"+formName+"');  \"")).append(style).append(" >").append(getPreviousText()).append("</a>");
			
			appendpageItems(sbOut);
			
			sbOut.append("<a ").append(isLast?disabled:("href=\"javascript:paper_submit("+(pageIndex+1)+",'"+formName+"');  \"")).append(style).append(" >").append(getNextText()).append("</a>");
			sbOut.append("<a ").append(isLast?disabled:("href=\"javascript:paper_submit("+(pageCount-1)+",'"+formName+"');  \"")).append(style).append(" >").append(getLastText()).append("</a>");
			
		}else {
			sbOut.append("<a ").append(isFirst?disabled:firstUrl).append(style).append(" >").append(getFirstText()).append("</a>");
			sbOut.append("<a ").append(isFirst?disabled:previousUrl).append(style).append("\">").append(getPreviousText()).append("</a>");
			
			appendpageItems(sbOut);
			
			sbOut.append("<a ").append(isLast?disabled:nextUrl).append(style).append("\">").append(getNextText()).append("</a>");
			sbOut.append("<a ").append(isLast?disabled:lastUrl).append(style).append("\">").append(getLastText()).append("</a>");
		}
		sbOut.append("</span>");
	}

	private void appendpageItems(StringBuilder sbOut) {
		if (pageCount>1&&showPageItems) {
			int startItem = pageIndex/10*10;
			int endItem =(startItem+10)>pageCount?pageCount:startItem+10;
			
			
			boolean showStartMore = (pageIndex)/10>0; //�Ƿ��и�������
			boolean showEndMore=endItem<pageCount;
			
			if (postSubmit) { //post�ύ
				if (showStartMore) 
					sbOut.append("<a href=\"javascript:paper_submit(").append(startItem-1).append("); \" ").append(style).append(">...</a>");
				
				for (int i = 0; i < (endItem-startItem); i++)
					sbOut.append("<a href=\"javascript:paper_submit(").append(startItem+i).append("); \" ").append(pageIndex==i?redStyle:style).append(">").append(startItem+i+1).append("</a>");

				if (showEndMore)
					sbOut.append("<a href=\"javascript:paper_submit(").append(endItem).append("); \" ").append(style).append(">...</a>");
			}else {  //get�ύ
				if (showStartMore) 
					sbOut.append("<a href=\"").append(requestUrl).append(hasParams?"&":"?").append("p=").append(startItem-1).append("\" ").append(style).append(">...</a>");
				
				for (int i = 0; i < (endItem-startItem); i++) 
					sbOut.append("<a href=\"").append(requestUrl).append(hasParams?"&":"?").append("p=").append(startItem+ i).append("\"").append(pageIndex==i?redStyle:style).append(">").append(startItem+i+1).append("</a>");
				
				if (showEndMore)
					sbOut.append("<a href=\"").append(requestUrl).append(hasParams?"&":"?").append("p=").append(endItem).append("\" ").append(style).append(">...</a>");
			}
		}
	}

	private void appendPageInfo(StringBuilder sbOut) {
		if (showPageInfo) {
			sbOut.append("<span class=\"paper_pageInfo\" >").append(getPageInfo()).append("</span>");
		}
	}

	private void appendInputBox(StringBuilder sbOut) {
		if (postSubmit&&showInputBox) {
			sbOut.append("<span class=\"paper_box\">");
			sbOut.append("<input type=\"text\" id=\"paperIndex\" name=\"p\" class=\"paper_input\" value=\"").append(pageIndex+1).append("\" style=\"width:30px;margin:0px 5px;\" onkeydown=\"paper_keydown(event,'pagerIndex');\" />");
			sbOut.append("<input type=\"submit\" id=\"paperButton\" class=\"paper_submit\" style=\"margin:0px 5px;\" value=\"").append(submitText).append("\" onclick=\"return paper_checkInput('paperIndex',").append(pageCount).append(");\" />");
			sbOut.append("</span>");
		}else if (postSubmit) {
			sbOut.append("<input type=\"hidden\" id=\"paperIndex\" name=\"p\" value=\"").append(pageIndex).append("\" />");
		}
	}
	

	public Integer getPageIndex() {
		if (request.getParameter("p")!=null) {
			pageIndex=parseInteger(request.getParameter("p"));
		}
		else {
			pageIndex=0;
		}
		return pageIndex;
	}
	
	
	private Integer parseInteger(Object object)
	{
		return Integer.parseInt(object.toString());
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		pageCount=recordCount/pageSize+((recordCount%pageSize)>0?1:0);
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getRequestUrl() {
		
		if (StringUtil.IsNullOrEmpty(requestUrl)) {
			requestUrl=request.getRequestURL().toString();
		}
		
		if (!postSubmit) {
			if (request.getParameterMap().size()>0) {
				Map<?,?> map= request.getParameterMap();
				
				hasParams=false;
				
				for (Object key : map.keySet()) {
					if ("p".equals(key)) {
						continue;
					}
					if (StringUtil.IsNullOrEmpty(request.getParameter(key.toString()))) {
						continue;
					}
					if (hasParams) {
						requestUrl+="&"+key+"="+request.getParameter(key.toString());
					}else {
						requestUrl+="?"+key+"="+request.getParameter(key.toString());
						hasParams=true;
					}
					
				}
				
			}
		}
		
		return requestUrl;
	}
	
	private void appendHiddenField(StringBuilder sbOut,String name,String value) {
		sbOut.append("<input type=\"hidden\" name=\""+name+"\" value=\""+value+"\" />");
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	
	
	public boolean isAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(boolean alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	public String getFirstText() {
		if (firstText==null) {
			return "��ҳ";
		}
		return firstText;
	}

	public void setFirstText(String firstText) {
		this.firstText = firstText;
	}

	public String getPreviousText() {
		if (previousText==null) {
			return "��һҳ";
		}
		return previousText;
	}

	public void setPreviousText(String previousText) {
		this.previousText = previousText;
	}

	public String getNextText() {
		if (nextText==null) {
			return "��һҳ";
		}
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getLastText() {
		if (lastText==null) {
			return "ĩҳ";
		}
		return lastText;
	}

	public void setLastText(String lastText) {
		
		this.lastText = lastText;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Boolean getShowInputBox() {
		return showInputBox;
	}

	public void setShowInputBox(Boolean showInputBox) {
		this.showInputBox = showInputBox;
	}

	public String getSubmitText() {
		return submitText;
	}

	public void setSubmitText(String submitText) {
		this.submitText = submitText;
	}

	public  String getScript() {
		if (StringUtil.IsNullOrEmpty(script)) {
			
			InputStream iStream= PaperTag.class.getResourceAsStream("js/paperScript.js");
			
			script="<script type=\"text/javascript\">\n"+ new FileOperation().read(iStream).toString()+"\n</script>\n";
		}
		
		return script;
	}

	public Boolean getPostSubmit() {
		return postSubmit;
	}

	public void setPostSubmit(Boolean postSubmit) {
		this.postSubmit = postSubmit;
	}

	
	public String getPageInfo() {
		if (StringUtil.IsNullOrEmpty(pageInfo)) {
			return "��ǰ��"+(pageIndex+1)+"ҳ����"+pageCount+"ҳ";
		}
		pageInfo=pageInfo.replace("@p", String.valueOf(pageIndex+1));
		pageInfo=pageInfo.replace("@total", String.valueOf(pageCount));
		pageInfo=pageInfo.replace("@record", String.valueOf(recordCount));
		pageInfo=pageInfo.replace("@size", String.valueOf(pageSize));
		return pageInfo;
	}

	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Boolean getShowPageInfo() {
		return showPageInfo;
	}

	public void setShowPageInfo(Boolean showPageInfo) {
		this.showPageInfo = showPageInfo;
	}

	public Boolean getShowPapeInfoLeft() {
		return showPapeInfoLeft;
	}

	public void setShowPapeInfoLeft(Boolean showPapeInfoLeft) {
		this.showPapeInfoLeft = showPapeInfoLeft;
	}

	public Boolean getShowPageItems() {
		return showPageItems;
	}

	public void setShowPageItems(Boolean showPageItems) {
		this.showPageItems = showPageItems;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}


}
