package com.zt.util;

import java.text.DecimalFormat;

/**
 * ���ָ�ʽ��������
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
