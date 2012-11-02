package com.zt.util;

import java.math.BigDecimal;


/**
 * ¿‡–Õ∏®÷˙¿‡
 * @author zt
 *
 */
public class TypeUtil {
	
	public static Object Change(Object obj,Class<?> clazz)
	{
		if (obj==null) {
			return null;
		}
		
		String typeName= clazz.getSimpleName();
		//System.out.println(typeName);
		if (typeName.equalsIgnoreCase("string")) {
			return obj.toString();
		}else if(typeName.equalsIgnoreCase("int")||typeName.equalsIgnoreCase("integer")) {
			return Integer.parseInt(obj.toString());
		}else if(typeName.equalsIgnoreCase("double")) {
			return obj.toString();
		}else if(typeName.equalsIgnoreCase("float")) {
			return Float.parseFloat(obj.toString());
		}else if (typeName.equalsIgnoreCase("boolean")){
			if ("1".equals(obj)||"y".equalsIgnoreCase(obj.toString())) return true;
			if ("0".equals(obj)||"n".equalsIgnoreCase(obj.toString())) return false;
			
			return Boolean.parseBoolean(obj.toString());
		}else if(typeName.equalsIgnoreCase("short")){
			return Short.parseShort(obj.toString());
		}else if(typeName.equalsIgnoreCase("bigdecimal")){
			return BigDecimal.valueOf(Double.parseDouble(obj.toString()));
		}
		else {
			return obj;
		}
		
	}
	
}
