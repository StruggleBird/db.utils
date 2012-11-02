package com.zt.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.zt.util.StringUtil;

/**
 * 用户登录过滤器
 * @author zt
 * @since 2010-3-20
 * 配置方法:(将一下filter节点配置copy到web.xml根节点下)
 * <filter>
		<filter-name>userLoginFilter</filter-name>
		<filter-class>com.zt.web.filter.UserLoginFilter</filter-class>
		<init-param>
			<param-name>userKey</param-name>
			<param-value>user</param-value>
		</init-param>
		<init-param>
			<param-name>loginUrl</param-name>
			<param-value>/webDomain/login.jsp</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>userLoginFilter</filter-name>
		<url-pattern>/member/*.jsp</url-pattern>
		<url-pattern>/member/*.do</url-pattern>
	</filter-mapping>
 *备注：可以在web.xml中配置多个filter节点以限制不同目录中用户权限的访问
 */
public class UserLoginFilter implements Filter {

	protected static final String USER_KEY="userKey";
	
	protected static final String LOGIN_URL="loginUrl";
	
	protected String userKey;
	
	protected String loginUrl;
	
	public UserLoginFilter() {
		this.userKey="USER";
	}
	
	public void init(FilterConfig config) throws ServletException {
		
		if (StringUtil.IsNullOrEmpty(config.getInitParameter(USER_KEY))) {
			throw new RuntimeException("沒有为"+this.getClass().getSimpleName()+"配置初始化参数："+USER_KEY+"！");
		}
		
		if (StringUtil.IsNullOrEmpty(config.getInitParameter("loginUrl"))) {
			throw new RuntimeException("沒有为"+this.getClass().getSimpleName()+"配置初始化参数："+LOGIN_URL+"！");
		}
	
		userKey=config.getInitParameter(USER_KEY);
		loginUrl=config.getInitParameter(LOGIN_URL);
		
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req =(HttpServletRequest)request;
		if (req.getSession(false)==null||req.getSession().getAttribute(userKey)==null) {
			((HttpServletResponse)response).sendRedirect(loginUrl);
		}
	}

}
