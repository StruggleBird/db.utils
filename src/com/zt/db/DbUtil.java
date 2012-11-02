package com.zt.db;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ���ݲ���������ӿ�
 * @author zt
 * @since 2010-3-6
 */
public abstract interface DbUtil extends Transaction{

	
	/**
	 * ִ�д����ⳤ�Ȳ�����SQL���
	 * @param sql SQL���������CRUD��SP
	 * @param params SQL��������Ĳ���
	 * @return ��Ӱ�������
	 */
	@SuppressWarnings("finally")
	public abstract int execute(String sql, Object... params);

	
	/**
	 * ��ȡ����ʵ��
	 * @param <T> Ҫ����ʵ�����
	 * @param clazz Ҫ����ʵ����������
	 * @param id Ҫ��ȡ�Ķ���ID
	 * @return
	 */
	public abstract <T> T getObj(Class<T> clazz,Serializable id);
	
	/**
	 * ��ȡ����ʵ�����
	 * @param <T> 
	 * @param objExample ʵ����� ���а������ֻ�ȫ�����Ե�ֵ
	 * @return ���ذ����������������Ե�Ψһʵ��
	 */
	public abstract <T> T getObj(T objExample);
	
	
	/**
	 * ��ȡ��������
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings( { "finally", "unchecked" })
	public abstract <T> T getObj(Class<T> clazz, String sql, Object... params);

	/**
	 * ��ȡ���󼯺��б�
	 * @param <T>
	 * @param clazz �б��а�����ʵ��������
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz);
	
	/**
	 * ��ȡ���󼯺��б�
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
	 * ��ȡ���󼯺��б� ʵ�ַ�ҳ��ѯ
	 * @param <T>
	 * @param clazz ��ѯ������
	 * @param pageSize ��ѯ��������
	 * @param pageIndex ��ǰҳ��
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex);
	
	
	/**
	 * ��ȡ���󼯺��б� ʵ�ַ�ҳ��ѯ
	 * @param <T>
	 * @param clazz ��ѯ������
	 * @param tableName ����������Ӧ�ı�����
	 * @param uniqueColumn ���е�����Ψһ��
	 * @param pageSize ��ѯ��������
	 * @param pageIndex ��ǰҳ��
	 * @param where ��ѯ����
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex, String where);
	
	/**
	 * ��ȡ���󼯺��б� ʵ�ַ�ҳ��ѯ
	 * @param <T>
	 * @param clazz ��ѯ������
	 * @param tableName ����������Ӧ�ı�����
	 * @param uniqueColumn ���е�����Ψһ��
	 * @param pageSize ��ѯ��������
	 * @param pageIndex ��ǰҳ��
	 * @param where ��ѯ����
	 * @param order ������
	 * @return
	 */
	public abstract <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex, String where,String order);
	
	
	/**
	 * ���ݶ����ȡӵ�иö������Ե����ж���
	 * @param obj 
	 * @param tableName
	 * @return
	 */
	public abstract <T> List<T> getObjListByExample(T objExample);
	
	
	/**
	 * ��ȡ����ֵ�ļ���
	 * @param <T>
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("finally")
	public abstract <T> List<T> getSingleValList(String sql, Object...params);

	/**
	 * ��ȡ����ֵ
	 * @param <T>
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("finally")
	public abstract <T> T getSingleValue(String sql, Object... params);


	/**
	 * �޸�ʵ�����
	 * @param obj
	 * @return
	 */
	public abstract boolean modify(Object obj);

	
	/**
	 * ���ʵ�����
	 * @param obj
	 * @return
	 */
	public abstract boolean add(Object obj);
	
	/**
	 * ɾ������ͨ������ID
	 * @param obj
	 * @return
	 */
	public abstract boolean delete(Object obj);
	
	/**
	 * ɾ������ͨ������ID
	 * @param obj
	 * @return �����Ƿ�ɹ�
	 */
	public abstract boolean deleteByExample(Object obj);
	
	
	/**
	 * �ж�ʵ������Ƿ����
	 * @param obj
	 */
	public abstract boolean exist(Object objExample);
	
	/**
	 * ִ�пɴ��������κ�����
	 * @param commands SQL����Ͳ����ļ���
	 * @return ÿ��SQL����ִ�����ݿ�����Ӱ�������
	 */
	public abstract int[] execTransaction(LinkedHashMap<String, Object[]> commands);
	
	/**
	 * ִ�пɴ��������κ�����
	 * @param commands SQL����Ͳ����ļ���
	 * @return ����ִ���Ƿ�ɹ�
	 */
	public abstract boolean executeTransaction(LinkedHashMap<String, Object[]> commands);
	
	
}