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
	 * 从请求参数中获取实体对象的值
	 * @param <T> 表单参数所属的实体对象
	 * @param clazz 表单参数所属的实体对象
	 * @param request 当前请求
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
	 * 弹框提示
	 * @param response 当前响应的Response
	 * @param msg 提示内容
	 * @throws IOException
	 */
	public static void alert(HttpServletResponse response,String msg) throws IOException {
		response.getWriter().println("<script type=\"text/javascript\">alert(\""+msg+"\");</script>");
	}
	
	/**
	 * 弹框提示并跳转
	 * @param response 当前响应的Response
	 * @param msg 提示内容
	 * @param url 跳转到的URL
	 * @throws IOException
	 */
	public static void alertAndRedirect(HttpServletResponse response,String msg,String url) throws IOException {
		response.getWriter().println("<script type=\"text/javascript\">alert('"+msg+"');location.href='"+url+"';</script>");
	}
	
}
