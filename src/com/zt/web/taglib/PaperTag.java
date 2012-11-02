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
 * 分页标签
 * @author zt
 * @since 2010-3-6
 */
public class PaperTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 772405902899222598L;
	
	private Integer pageIndex=0; //当前页面索引
	
	private Integer pageSize=10; //当前页面显示数据行的大小 输入参数
	
	private Integer pageCount; //所有数据所占据的总页数
	
	private String requestUrl; //当前页面的Url
	
	private String formName="paper_form"; //当前分页控件所在的表单名称
	
	private Integer recordCount; //总数据行数
	
	private Boolean alwaysShow=false; //总是显示
	
	private String cssClass; //分页控件外div的class属性
	
	private Boolean postSubmit=false;
	
	private Boolean showInputBox=false; //是否显示输入文本框
	
	private String submitText="转到"; //提交按钮文本
	
	private String pageInfo; //当前分页信息
	
	private Boolean showPapeInfoLeft=false; //将paperInfo显示在左边或是右边
	
	private Boolean showPageItems =false; //是否显示连接项
	
	private Boolean showPageInfo=false;
	
	private String firstText;
	
	private String previousText;
	
	private String nextText;
	
	private String lastText;
	
	private static String script=null;

	private boolean isFirst; //当前页是否是第一页
	
	private boolean isLast; //当前页是否是最后一页
	
	private boolean hasParams=false; //是否有Url的其它参数
	
	
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
		
		
		appendScript(sbOut); //追加script脚本
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
		if (postSubmit) { //如果为Post提交
			
			
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
			
			
			boolean showStartMore = (pageIndex)/10>0; //是否还有更多数据
			boolean showEndMore=endItem<pageCount;
			
			if (postSubmit) { //post提交
				if (showStartMore) 
					sbOut.append("<a href=\"javascript:paper_submit(").append(startItem-1).append("); \" ").append(style).append(">...</a>");
				
				for (int i = 0; i < (endItem-startItem); i++)
					sbOut.append("<a href=\"javascript:paper_submit(").append(startItem+i).append("); \" ").append(pageIndex==i?redStyle:style).append(">").append(startItem+i+1).append("</a>");

				if (showEndMore)
					sbOut.append("<a href=\"javascript:paper_submit(").append(endItem).append("); \" ").append(style).append(">...</a>");
			}else {  //get提交
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
			return "首页";
		}
		return firstText;
	}

	public void setFirstText(String firstText) {
		this.firstText = firstText;
	}

	public String getPreviousText() {
		if (previousText==null) {
			return "上一页";
		}
		return previousText;
	}

	public void setPreviousText(String previousText) {
		this.previousText = previousText;
	}

	public String getNextText() {
		if (nextText==null) {
			return "下一页";
		}
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getLastText() {
		if (lastText==null) {
			return "末页";
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
			return "当前第"+(pageIndex+1)+"页，共"+pageCount+"页";
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
