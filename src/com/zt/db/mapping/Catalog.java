package com.zt.db.mapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ���ݿ�ӳ����
 * @author zt
 * @since 2010-3-7
 */
public class Catalog {
	
	private static String name;
	
	private static Map<String, Table> tablesMap=new HashMap<String, Table>();
	
	public Catalog() {
		
		try {
			Class.forName("javax.persistence.Entity");
			Class.forName("javax.persistence.Column");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ʵ����ӳ�䵽����Ŀ¼��
	 * @param clazz ʵ����Class
	 */
	public static synchronized void registerTable(Class<?> clazz) {
		if (Catalog.getTablesMap().containsKey(clazz.getSimpleName())) {
			return;
		}
	
		if (clazz.isAnnotationPresent(Entity.class)) {
			Entity entity = (Entity) clazz.getAnnotation(Entity.class);
			Table table = new Table(entity.name());

			Catalog.addTableMap(clazz.getSimpleName(), table);

			Field[] fields = clazz.getDeclaredFields();
			boolean hasPrimaryKey=false;
			for (Field field : fields) {
				if (field.isAnnotationPresent(Column.class)) {
					//��ȡ�ֶ�����Ӧ�����ݿ�������
					Column column = field.getAnnotation(Column.class);
					 boolean isPrimary = field.isAnnotationPresent(Id.class);
					 
					 if(!hasPrimaryKey)
						 hasPrimaryKey=isPrimary;
					 
					table.addColumnMap(field.getName(), new com.zt.db.mapping.Column(column.name(),field.getName(),isPrimary));
				}

			}
			
			if (!hasPrimaryKey) {
				throw new RuntimeException("û����ʵ���������ֶ������@Id��ʶ �� û���κ������б���ֵ��");
			}

		}else {
			throw new RuntimeException("û����ʵ��������ʱ��� @Entity��ʶ��");
		}
	}
	
	/**
	 * ��ӱ�ӳ�䵽����
	 * @param tName 
	 * @param table
	 */
	public static void addTableMap(String persistentClassName,Table table)
	{
		if (tablesMap.containsKey(persistentClassName)) {
			return;
		}
		
		tablesMap.put(persistentClassName, table);
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Catalog.name = name;
	}

	public static Map<String, Table> getTablesMap() {
		return tablesMap;
	}

	public static void setTablesMap(Map<String, Table> tablesMap) {
		Catalog.tablesMap = tablesMap;
	}

	
}
