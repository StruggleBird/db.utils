package com.zt.utils.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String������
 * @author zt
 *
 */
public class StringUtil {
	
	public static final String[] STRINGS_MODE = new String[0];
	
	/**
	 * �ж��ַ����Ƿ�ΪNull ���� ""
	 * @param str Ҫ�����жϵ��ַ���
	 * @return �Ƿ�Ϊ��
	 */
	public static boolean IsNullOrEmpty(Object str)
	{
		if (str==null||"".equals(str.toString().trim())) {
			return true;
		}
		return false;
	}
	/**
	 * �ж��ַ����Ƿ�ΪNULL
	 * @param str  Ҫ�����жϵ��ַ���
	 * @return �Ƿ�ΪNULL
	 */
	public static boolean IsNull(Object str)
	{
		return str==null;
	}
	
	/**
	 * �÷ָ�����������������,ת����Ϊ�ַ�������
	 * @param seperator �ָ���
	 * @param strings Ҫ��ӷָ������ַ���
	 */
	public static String join(String seperator, String[] strings) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);

	    for (int i = 1; i < length; ++i)
	      buf.append(seperator).append(strings[i]);

	    return buf.toString();
	  }
	
	/**
	 * �÷ָ�����������������,ת����Ϊ�ַ�������
	 * @param seperator �ָ���
	 * @prefix ǰ׺�ַ�
	 * @param strings Ҫ��ӷָ������ַ���
	 * @suffix ��׺�ַ�
	 */
	public static String join(String seperator,char prefix,  String[] strings,char suffix) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuffer buf = new StringBuffer().append(prefix).append(strings[0]).append(suffix);

	    for (int i = 1; i < length; ++i)
	      buf.append(seperator).append(prefix).append(strings[i]).append(suffix);

	    return buf.toString();
	  }
	
	
	
	/**
	 * ��������ÿһ����׷��ָ���ַ���
	 * @param appendItem ׷����
	 * @param strings ԭ����
	 * @return
	 */
	public static String append(String appendItem,String[] strings) {
		if (strings.length==0) {
			return "";
		}
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			sBuilder.append(strings[i]).append(appendItem);
		}
		return sBuilder.toString();
	}
	
	/**
	 * ��ȡ�ַ���
	 * ���Ҫ��ȡ���ַ������ֽڳ���С��byteLength�򷵻������ַ���
	 * �����ȡbyteLength���ȵ��ֽ������ַ�������
	 * @param str Ҫ���н�ȡ���ַ���
	 * @param byteLength Ҫ��ȡ�ַ������ֽڳ���
	 */
	public static String subString(String str,int byteLength)
	{
		Pattern pattern =Pattern.compile("[\\u4E00-\\u9FA5\\uF900-\\uFA2D]");
		
		if (StringUtil.IsNullOrEmpty(str)||byteLength<=0) {
			return "";
		}
		StringBuilder sbResult = new StringBuilder();
		int size=0;
		for (int i = 0; i < str.length(); i++) {
			Matcher m = pattern.matcher(str.substring(i, i+1));
			
			size+=m.matches()?2:1;
			sbResult.append(str.charAt(i));
			if (size>=byteLength) {
				break;
			}
		}
		
		return sbResult.toString();
	}
	
	/**
	 * ��������ÿһ����׷��ָ���ַ���
	 * @param appendItem ׷����
	 * @param strings ԭ����
	 */
	public static String append(String appendItem,char prefix,  String[] strings,char suffix) {
		if (strings.length==0) {
			return "";
		}
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			sBuilder.append(prefix).append(strings[i]).append(suffix).append(appendItem);
		}
		return sBuilder.toString();
	}
	
	/**
	 * ��������ÿһ��ǰ�����ָ���ַ�
	 * @param params
	 */
	public static String[] superaddition(char prefix, String[] params,char suffix) {
		String[] array = new String[params.length];
		for (int i = 0; i < array.length; i++) {
			array[i]=prefix+params[i]+suffix;
		}
		
		return array;
	}
	
	
}
