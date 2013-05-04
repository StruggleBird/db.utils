package com.zt.utils.commons;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author zt
 * @since 2010-3-7
 */
public class ArrayUtil {
	
public static List<String> toList(String...arrays) {
		
		List<String> list = new ArrayList<String>();
		
		for (String s : arrays) {
			list.add(s);
		}
		
		return list;
	}
	
	public static List<String> toList(String[]...arrays) {
		
		List<String> list = new ArrayList<String>();
		
		for (String[] array : arrays) {
			for (String string : array) {
				list.add(string);
			}
		}
		return list;
	}
	
	
}
