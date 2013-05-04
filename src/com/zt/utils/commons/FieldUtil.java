package com.zt.utils.commons;

/**
 * 
 * @author zt
 * @since 2010-3-7
 */
public class FieldUtil {
	
	
	public static String[] getDeclaredFields(Class<?> clazz ) {
		
		String[] fields = new String[clazz.getDeclaredFields().length];
		
		for (int i = 0; i < fields.length; i++) {
			fields[i]=clazz.getDeclaredFields()[i].getName();
		}
		return fields;
		
	}
	
	
}
