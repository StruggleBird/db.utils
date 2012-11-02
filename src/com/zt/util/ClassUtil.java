package com.zt.util;

/**
 * ¿‡
 * @author zt
 * @since 2010-3-21
 */
public class ClassUtil {
	
	public static <T> T getInstance(Class<T> clazz) 
	{
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
