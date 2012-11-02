package com.zt.db;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 数据操作工具类接口
 * @author zt
 * @since 2010-3-6
 */
public abstract interface DbUtil extends Transaction{

	
	/**
	 * 执行带任意长度参数的SQL语句
	 * @param sql SQL命令可以是CRUD或SP
	 * @param params SQL命令所需的参数
	 * @return 受影响的行数
	 */
	@SuppressWarnings("finally")
	public abstract int execute(String sql, Object... params);

	
	/**
	 * 获取单个实体
	 * @param <T> 要返回实体对象
	 * @param clazz 要返回实体对象的类型
	 * @param id 要获取的对象ID
	 * @return
	 */
	public abstract <T> T getObj(Class<T> clazz,Serializable id);
	
	/**
	 * 获取单个实体对象
	 * @param <T> 
	 * @param objExample 实体对象 其中包含部分或全部属性的值
	 * @return 返回包含参数对象中属性的唯一实体
	 */
	public abstract <T> T getObj(T objExample);
	
	
	/**
	 * 获取单个对象
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings( { "finally", "unchecked" })
	public abstract <T> T getObj(Class<T> clazz, String sql, Object... params);

	/**
	 * 获取对象集合列表
	 * @param <T>
	 * @param clazz 列表中包含的实体类名称
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz);
	
	/**
	 * 获取对象集合列表
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("finally")
	public abstract <T> List<T> getObjList(Class<T> clazz, String sql,
			Object... params);

	
	/**
	 * 获取对象集合列表 实现分页查询
	 * @param <T>
	 * @param clazz 查询对象类
	 * @param pageSize 查询的最大个数
	 * @param pageIndex 当前页数
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex);
	
	
	/**
	 * 获取对象集合列表 实现分页查询
	 * @param <T>
	 * @param clazz 查询对象类
	 * @param tableName 对象类所对应的表名称
	 * @param uniqueColumn 表中的任意唯一列
	 * @param pageSize 查询的最大个数
	 * @param pageIndex 当前页数
	 * @param where 查询条件
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex, String where);
	
	/**
	 * 获取对象集合列表 实现分页查询
	 * @param <T>
	 * @param clazz 查询对象类
	 * @param tableName 对象类所对应的表名称
	 * @param uniqueColumn 表中的任意唯一列
	 * @param pageSize 查询的最大个数
	 * @param pageIndex 当前页数
	 * @param where 查询条件
	 * @param order 排序列
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex, String where,String order);
	
	
	/**
	 * 根据对象获取拥有该对象属性的所有对象
	 * @param obj 
	 * @param tableName
	 * @return
	 */
	public abstract <T> List<T> getObjListByExample(T objExample);
	
	
	/**
	 * 获取单个值的集合
	 * @param <T>
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("finally")
	public abstract <T> List<T> getSingleValList(String sql, Object...params);

	/**
	 * 获取单个值
	 * @param <T>
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("finally")
	public abstract <T> T getSingleValue(String sql, Object... params);


	/**
	 * 修改实体对象
	 * @param obj
	 * @return
	 */
	public abstract boolean modify(Object obj);

	
	/**
	 * 添加实体对象
	 * @param obj
	 * @return
	 */
	public abstract boolean add(Object obj);
	
	/**
	 * 删除对象通过对象ID
	 * @param obj
	 * @return
	 */
	public abstract boolean delete(Object obj);
	
	/**
	 * 删除对象通过对象ID
	 * @param obj
	 * @return 操作是否成功
	 */
	public abstract boolean deleteByExample(Object obj);
	
	
	/**
	 * 判断实体对象是否存在
	 * @param obj
	 */
	public abstract boolean exist(Object objExample);
	
	/**
	 * 执行可带参数的任何事物
	 * @param commands SQL命令和参数的集合
	 * @return 每条SQL命令执行数据库所受影响的行数
	 */
	public abstract int[] execTransaction(LinkedHashMap<String, Object[]> commands);
	
	/**
	 * 执行可带参数的任何事物
	 * @param commands SQL命令和参数的集合
	 * @return 事物执行是否成功
	 */
	public abstract boolean executeTransaction(LinkedHashMap<String, Object[]> commands);
	
	
}