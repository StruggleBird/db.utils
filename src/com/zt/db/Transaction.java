package com.zt.db;

/**
 * ����ӿ�
 * @author zt
 * @since 2010-3-12
 */
public interface Transaction {

	public abstract void beginTransaction();

	public abstract void commitTransaction();

	public abstract void rollbackTransaction();

}