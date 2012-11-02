package com.zt.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ×Ö·û±àÂë¹ýÂËÆ÷
 * @author zt
 * <!--Config in web.xml of Application -->
 *<filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>
			com.zt.web.filter.CharacterEncodingFilter
		</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
		<init-param>
			<param-name>enable</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>*.jsp</url-pattern>
		<url-pattern>*.do</url-pattern>
		<!-- <url-pattern>*.do</url-pattern> -->
	</filter-mapping>
 *
 */
public class CharacterEncodingFilter implements Filter {

  protected String encodingName;
  protected boolean enable;

  public CharacterEncodingFilter() {
    this.encodingName = "utf-8";
    this.enable = true;
  }

  public void init(FilterConfig filterConfig) throws ServletException {
	  
    if (filterConfig.getInitParameter("encoding")!=null) {
    	this.encodingName=filterConfig.getInitParameter("encoding");
	}
    
    if (filterConfig.getInitParameter("enable")!=null) {
    	this.enable=Boolean.parseBoolean(filterConfig.getInitParameter("enable"));
	}
    
  }

  public void doFilter(ServletRequest request, 
                       ServletResponse response,
                       FilterChain chain) 
                    throws IOException, ServletException {
	  
    request.setCharacterEncoding(encodingName);
    response.setCharacterEncoding(encodingName);
    response.setContentType("text/html;charset="+encodingName);
    chain.doFilter(request, response);
  }

  public void destroy() {
  }
}