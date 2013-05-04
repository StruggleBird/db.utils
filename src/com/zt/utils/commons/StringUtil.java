package com.zt.utils.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String辅助类
 * @author zt
 *
 */
public class StringUtil {
	
	public static final String[] STRINGS_MODE = new String[0];
	
	/**
	 * 判断字符串是否为Null 或是 ""
	 * @param str 要进行判断的字符串
	 * @return 是否为空
	 */
	public static boolean IsNullOrEmpty(Object str)
	{
		if (str==null||"".equals(str.toString().trim())) {
			return true;
		}
		return false;
	}
	/**
	 * 判断字符串是否为NULL
	 * @param str  要进行判断的字符串
	 * @return 是否为NULL
	 */
	public static boolean IsNull(Object str)
	{
		return str==null;
	}
	
	/**
	 * 用分隔符将数组连接起来,转换成为字符串返回
	 * @param seperator 分隔符
	 * @param strings 要添加分隔符的字符串
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
	 * 用分隔符将数组连接起来,转换成为字符串返回
	 * @param seperator 分隔符
	 * @prefix 前缀字符
	 * @param strings 要添加分隔符的字符串
	 * @suffix 后缀字符
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
	 * 在数组中每一项后边追加指定字符串
	 * @param appendItem 追加项
	 * @param strings 原数组
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
	 * 截取字符串
	 * 如果要截取的字符串的字节长度小于byteLength则返回整个字符串
	 * 否则截取byteLength长度的字节数的字符串返回
	 * @param str 要进行截取的字符串
	 * @param byteLength 要截取字符串的字节长度
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
	 * 在数组中每一项后边追加指定字符串
	 * @param appendItem 追加项
	 * @param strings 原数组
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
	 * 在数组中每一项前后加上指定字符
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
