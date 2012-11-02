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
	 * ע��mapֵ��������
	 * 
	 * @param cla
	 *            Ҫʵ������ʵ����
	 * @param map
	 *            �洢ʵ�������Ӧ���Եļ���
	 * @return ʵ�������
	 */
	private static <T> T ImmitValue(Class<T> clazz, SortedMap<?, ?> map) {
		
		T t=null;
		try {
			t = clazz.newInstance();
			
			Table table= Catalog.getTablesMap().get(clazz.getSimpleName());
			
			Map<String, Column> columnsMap = table.getColumnsMap();
			Set<?> dbColumnNames = map.keySet();

			for (Object columnName : dbColumnNames) { //������ȡ��������
				
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
	 * ��SortedMap�л�ȡʵ�弯��
	 * @param cla
	 * @param maps
	 * @return ʵ�����б�List<Object>
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
	 * ��Resultת��Ϊʵ�����б�
	 * 
	 * @param cla
	 *            ʵ��������
	 * @param result
	 *            result����
	 * @return ʵ�����б�List<Object>
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
