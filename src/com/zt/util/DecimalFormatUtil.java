package com.zt.util;

import java.text.DecimalFormat;

/**
 * 数字格式化工具类
 * @author zt
 *
 */
public class DecimalFormatUtil {
	
	private static DecimalFormat decimalFormat= new DecimalFormat();
	
	public static String Format(Object object,String pattern) {
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(object);
	}
	
}
