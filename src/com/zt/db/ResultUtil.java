package com.zt.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import com.zt.db.mapping.Catalog;
import com.zt.db.mapping.Column;
import com.zt.db.mapping.Table;
import com.zt.util.TypeUtil;


/**
 * 
 * @author zt
 * @since 2009-09-28
 */
public class ResultUtil<T> {


	/**
	 * 注入map值到对象中
	 * 
	 * @param cla
	 *            要实例化的实体类
	 * @param map
	 *            存储实体对象相应属性的集合
	 * @return 实体类对象
	 */
	private static <T> T ImmitValue(Class<T> clazz, SortedMap<?, ?> map) {
		
		T t=null;
		try {
			t = clazz.newInstance();
			
			Table table= Catalog.getTablesMap().get(clazz.getSimpleName());
			
			Map<String, Column> columnsMap = table.getColumnsMap();
			Set<?> dbColumnNames = map.keySet();

			for (Object columnName : dbColumnNames) { //遍历读取的列名称
				
				for (Column column : columnsMap.values()) {
					if (column.getName().equals(columnName)) {
						String fieldName= column.getFieldName();
						Field field = clazz.getDeclaredField(fieldName);
						field.setAccessible(true);
//						System.out.println("columnType"+map.get(columnName).getClass());
//						System.out.println("fieldType"+field.getType());
						
						field.set(t, TypeUtil.Change(map.get(columnName), field.getType()) );
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return t;
	}
	
	/**
	 * 从SortedMap中获取实体集合
	 * @param cla
	 * @param maps
	 * @return 实体类列表List<Object>
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private static <T> List<T> getList(Class<T> cla, SortedMap<?, ?>[] maps)
			throws IllegalArgumentException, 
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		List<T> objList = new ArrayList<T>();
		for (SortedMap<?, ?> map : maps) {
			T t = ResultUtil.ImmitValue(cla, map);
			objList.add(t);
		}
		return objList;
	}

	/**
	 * 将Result转型为实体类列表
	 * 
	 * @param cla
	 *            实体类类型
	 * @param result
	 *            result对象
	 * @return 实体类列表List<Object>
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static <T> List<T> getList(Class<T> cla, ResultSet rst)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {

		Result result = ResultSupport.toResult(rst);

		List<T> objList = null;

		SortedMap<?,?>[] maps = result.getRows();

		objList = ResultUtil.getList(cla, maps);

		return objList;
	}

	public static <T> T GetObject(Class<T> clazz, ResultSet rst) {
		T t = null;

		Result result = ResultSupport.toResult(rst);

		SortedMap<?,?>[] maps = result.getRows();
		if (maps.length==0) {
			return null;
		}
		
		try {
			t = ResultUtil.ImmitValue(clazz, maps[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;

	}
}
