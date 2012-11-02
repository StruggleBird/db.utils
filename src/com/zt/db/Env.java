package com.zt.db;

import java.io.InputStream;
import java.util.Properties;

public final class Env extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6731588504301875011L;
	
	private static Env instance;
	
	public static Env getInstance() {
		
		if (instance!=null) {
			return instance;
		}
		
		makeInstance();
		return instance;
		
	}
	
	private static synchronized void makeInstance() {
		if (instance==null) {
			instance=new Env();
		}
	}
	
	private Env(){
		InputStream iStream =getClass().getResourceAsStream("/db.properties"); //获取utils项目中的配置文件 已过时
		//InputStream iStream =System.getProperty("user.dir").getClass().getResourceAsStream("/db.properties");
		try {
			
			if (iStream==null) {
				throw new NullPointerException("the file db.properties is not found!");
			}
			
			load(iStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
