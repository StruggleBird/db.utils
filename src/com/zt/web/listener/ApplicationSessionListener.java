package com.zt.web.listener;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ������session������
 * @author zt
 * 
 * config in web.xml
 *<listener>
 	<listener-class>listener.ApplicationSessionListener</listener-class>
 *</listener>
 *
 */
public class ApplicationSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		
		
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		
	}
	
}
