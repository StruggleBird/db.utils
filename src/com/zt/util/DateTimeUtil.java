package com.zt.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 *  ±º‰∏®÷˙¿‡
 * @author zt
 *
 */
public class DateTimeUtil {
	
	private static SimpleDateFormat sdFormat= new SimpleDateFormat();
	
	private static final String DATE_PATTERN="yyyy-MM-dd";
	
	private static final String DATETIME_PATTERN="yyyy-MM-dd HH:mm:ss";
	
	
	
	public static Date getDate(String dateString) throws ParseException {
		sdFormat.applyPattern(DATETIME_PATTERN);
		return sdFormat.parse(dateString);
	}
	
	public static Date getDate(long longDate) throws ParseException {
		String dateString= sdFormat.format(new Date(longDate));
		return getDate(dateString);
	}
	
	public static String getDateTimeStr(Date date){
		sdFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdFormat.format(date);
	}
	
	public static String getDateStr(Date date){
		sdFormat= new SimpleDateFormat(DATE_PATTERN);
		return sdFormat.format(date);
	}
	
	public static String format(Date date,String pattern) {
		sdFormat= new SimpleDateFormat(pattern);
		return sdFormat.format(date);
	}
	
	public static String getNowStr() {
		sdFormat= new SimpleDateFormat(DATETIME_PATTERN);
		return sdFormat.format(new Date());
	}
	
	public static Date addDays(Date date,int day)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, day);
		calendar.setTime(date);
		return calendar.getTime();
	}
	
	public static Date addMonths(Date date,int month)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}
	
}
