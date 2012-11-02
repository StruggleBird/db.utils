package com.zt.db;

import com.zt.util.StringUtil;


public class DbUtilFactory {
	
	private static DbUtil dbUtil= null;
	
	public synchronized static DbUtil createDbUtilInstance() {
		if (dbUtil==null) {
			String dbUtilImplClassName = Env.getInstance().getProperty("dbUtilImplClassName");
			
			if (StringUtil.IsNullOrEmpty(dbUtilImplClassName)) {
				throw new RuntimeException("没有配置可以提供的数据库操作实现类！");
			}
			
			try {
				Class<?> dbUtilClass= Class.forName(dbUtilImplClassName);
				dbUtil=(DbUtil) dbUtilClass.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
		return dbUtil;
	}
}
