package com.zt.utils.commons;

import java.lang.reflect.Field;

public class MethodUtil {

	/**
	 * �ж�ָ�����е�ָ���ֶ��Ƿ����Get����
	 * @param clazz
	 * @param field
	 * @return boolean
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static boolean HasGetMethod(Class<?> clazz,Field field)
	{
		String getMethodName = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
		try {
			return clazz.getDeclaredMethod(getMethodName)!=null;
		} catch (Exception e) {
			return false;
		}
		
		
	}
	
	/**
	 * �ж�ָ�����е�ָ���ֶ��Ƿ����Set����
	 * @param clazz
	 * @param field
	 * @return boolean
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static boolean HasSetMethod(Class<?> clazz,Field field) throws SecurityException, NoSuchMethodException
	{
		String setMethodName = "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
		
		try {
			return clazz.getDeclaredMethod(setMethodName)!=null;
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
