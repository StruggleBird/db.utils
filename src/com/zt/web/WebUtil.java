package com.zt.web;

import java.beans.Encoder;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.wsdl.util.StringUtils;
import com.zt.util.StringUtil;


/**
 * 
 * @author zt
 *
 */
public class WebUtil {
	
	public static void redirect(HttpServletResponse response, String url) throws IOException {
		response.getWriter().println("<script type=\"text/javascript\">location.href=\""+url+"\";</script>");
	}
	
	/**
	 * ����������л�ȡʵ������ֵ
	 * @param <T> ������������ʵ�����
	 * @param clazz ������������ʵ�����
	 * @param request ��ǰ����
	 * @return
	 */
	public static <T> T getParamObj(Class<T> clazz,HttpServletRequest request){
		T t = null;
		try {
				
			t = clazz.newInstance();
			Field[] fields= t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Object value=request.getParameter(field.getName());
				
				if (value!=null) {
					value = com.zt.util.TypeUtil.Change(value, field.getType());
					field.set(t,value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
	
	/**
	 * ������ʾ
	 * @param response ��ǰ��Ӧ��Response
	 * @param msg ��ʾ����
	 * @throws IOException
	 */
	public static void alert(HttpServletResponse response,String msg) throws IOException {
		response.getWriter().println("<script type=\"text/javascript\">alert(\""+msg+"\");</script>");
	}
	
	/**
	 * ������ʾ����ת
	 * @param response ��ǰ��Ӧ��Response
	 * @param msg ��ʾ����
	 * @param url ��ת����URL
	 * @throws IOException
	 */
	public static void alertAndRedirect(HttpServletResponse response,String msg,String url) throws IOException {
		response.getWriter().println("<script type=\"text/javascript\">alert('"+msg+"');location.href='"+url+"';</script>");
	}
	
}
