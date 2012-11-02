package com.zt.web.taglib;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zt.util.StringUtil;

/**
 * 格式化输出标签
 * @author zt
 * @since 2010-3-6
 */
public class FormatTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7409685372561472545L;
	
	private String pattern;
	
	private Object target;

	@Override
	public int doEndTag() throws JspException {
		try {
			String result = formatTarget(target);
			pageContext.getOut().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	private String formatTarget(Object target)
  {
    Format format = null;
    
    if (StringUtil.IsNullOrEmpty(pattern)) {
		throw new NullPointerException("属性pattern不能为空！");
	}
   
    if (target instanceof String) {
       return ((String)target);
    }

    if (target instanceof Number) {
      
      try {
        format = NumberFormat.getNumberInstance();

        ((DecimalFormat)format).applyPattern(pattern);
      }
      catch (IllegalArgumentException e) {
        
        throw e;
      }
    }
    
    if (target instanceof Date) {      
    	format = new SimpleDateFormat(pattern);
    }

    if (format != null)
      return format.format(target);

     return target.toString();
  }

	
	
}
