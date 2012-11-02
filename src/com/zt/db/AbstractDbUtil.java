package com.zt.db;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Status;

import com.zt.db.mapping.Catalog;
import com.zt.db.mapping.Column;
import com.zt.db.mapping.Table;
import com.zt.db.sql.Delete;
import com.zt.db.sql.Insert;
import com.zt.db.sql.Select;
import com.zt.db.sql.Update;
import com.zt.db.sql.Where;
import com.zt.util.StringUtil;

/**
 * 
 * @author zt
 * @since 2010-3-6
 */
@SuppressWarnings("unchecked")
public abstract class AbstractDbUtil implements DbUtil {

	protected Connection conn;

	protected PreparedStatement pstmt;

	protected int status = Status.STATUS_UNKNOWN; // 事物当前状态

	protected boolean isCommitSuccessfully = true;

	@SuppressWarnings("finally")
	public int execute(String sql, Object... params) {

		int result = 0;
		try {
			pstmt = getConnection().prepareStatement(sql);
			
			addParams(pstmt, params);
			System.out.println(sql);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			if (status == Status.STATUS_ACTIVE)
				isCommitSuccessfully = false;
		} finally {
			reset();
			return result;
		}
	}

	/**
	 * 如果不存在事物 关闭操作对象 此方法确保事物的正常进行
	 */
	private void reset() {
		if (status != Status.STATUS_ACTIVE) {
			ConnectionManager.close(pstmt, conn);
		}
	}
	
	

	/**
	 * 确保当前连接不为空
	 * 
	 * @return
	 */
	protected Connection getConnection() {
		try {
			if (this.conn == null || this.conn.isClosed()) {
				conn = ConnectionManager.GetConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.conn;
	}
	
	public <T> T getObj(Class<T> clazz,Serializable id){
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		String primaryKeyName =table.getPrimaryColumnNames().get(0);
		Where where = new Where().eq(getWapperQuote(primaryKeyName), "?");
		Select select = new Select().setSelectClause("*").setFromClause(getWapperQuote(table.getName())).setWhereClause(where.getWhereClause());
		return getObj(clazz, select.toStatementString(), id);
	}

	public <T> T getObj(T objExample) {
		List<T> list = getObjListByExample(objExample);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取实体类所对应的所有 非空 数据列名称和值
	 * 
	 * @param <T>
	 * @param obj
	 * @param clazz
	 * @param table
	 * @return
	 * @throws IllegalAccessException
	 */
	protected <T> Map<String, Object> getNotNullColumns(T obj,
			boolean hasPrimaryColumn) throws IllegalAccessException {
		Class clazz = obj.getClass();
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		Map<String, Object> columns = new LinkedHashMap<String, Object>();
		for (Field field : clazz.getDeclaredFields()) {
			// 如果为数据列
			if (table.getColumnsMap().containsKey(field.getName())) {
				field.setAccessible(true);
				if (field.get(obj) != null) {

					Column column = table.getColumnsMap().get(field.getName());
					if ((!hasPrimaryColumn) && column.getIsPrimary()) {
						continue;
					}
					columns.put(column.getName(), field.get(obj));
				}

			}
		}
		return columns;
	}

	/**
	 * 获取与实体相关联的表中所有主键列
	 * 
	 * @param <T>
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected <T> Map<String, Object> getprimaryKeyColumns(T obj)
			throws IllegalArgumentException, IllegalAccessException {
		Class clazz = obj.getClass();
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		Map<String, Object> columns = new LinkedHashMap<String, Object>();
		for (Field field : clazz.getDeclaredFields()) {
			// 如果为数据列
			if (table.getColumnsMap().containsKey(field.getName())) {
				field.setAccessible(true);
				if (field.get(obj) != null) {
					Column column = table.getColumnsMap().get(field.getName());
					if (column.getIsPrimary()) {
						columns.put(column.getName(), field.get(obj));
					}
				}
			}
		}
		return columns;
	}

	@SuppressWarnings( { "finally", "unchecked" })
	public <T> T getObj(Class<T> clazz, String sql, Object... params) {
		ResultSet rest = null;
		T t = null;

		try {
			Catalog.registerTable(clazz);
			pstmt = getConnection().prepareStatement(sql);

			addParams(pstmt, params);
			System.out.println(sql);
			rest = pstmt.executeQuery();

			t = ResultUtil.GetObject(clazz, rest);
		} catch (Exception e) {
			e.printStackTrace();
			if (status == Status.STATUS_ACTIVE)
				isCommitSuccessfully = false;
		} finally {
			reset();
			return t;
		}
	}

	public <T> List<T> getObjList(Class<T> clazz) {
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		Select select = new Select().setFromClause(getWapperQuote(table.getName()));
		String selectClause = StringUtil.join(",", openQuote(), table
				.getColumnNames().toArray(StringUtil.STRINGS_MODE),
				closeQuote());
		select.setSelectClause(selectClause);

		return getObjList(clazz, select.toStatementString());
	}

	@SuppressWarnings("finally")
	public <T> List<T> getObjList(Class<T> clazz, String sql, Object... params) {
		Catalog.registerTable(clazz);
		ResultSet rest = null;

		List<T> entitys = null;
		try {
			System.out.println(sql);
			pstmt = getConnection().prepareStatement(sql);
			
			addParams(pstmt, params);

			rest = pstmt.executeQuery();

			entitys = (List<T>) ResultUtil.getList(clazz, rest);

		} catch (Exception e) {
			e.printStackTrace();
			if (status == Status.STATUS_ACTIVE)
				isCommitSuccessfully = false;
		} finally {
			reset();
			return entitys;
		}
	}

	public <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex) {

		return getObjList(clazz, pageSize, pageIndex, null);
	}

	public <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex,
			String where) {
		return getObjList(clazz, pageSize, pageIndex, where, null);
	}

	public <T> List<T> getObjListByExample(T objExample) {
		Class<T> clazz = (Class<T>) objExample.getClass();
		Catalog.registerTable(clazz);
		try {

			Select select = new Select();
			Table table = Catalog.getTablesMap().get(clazz.getSimpleName());

			String selectClause = StringUtil.join(",", openQuote(), table
					.getColumnNames().toArray(StringUtil.STRINGS_MODE),
					closeQuote());
			select.setSelectClause(selectClause);
			select.setFromClause(getWapperQuote(table.getName()));

			Map<String, Object> columns = getNotNullColumns(objExample, true);
			if (!columns.isEmpty()) {
				String whereClause = StringUtil.append("=? and ", openQuote(),
						columns.keySet().toArray(StringUtil.STRINGS_MODE),
						closeQuote());
				whereClause = whereClause
						.substring(0, whereClause.length() - 4);
				select.setWhereClause(whereClause);
			}

			List<T> results = getObjList(clazz, select.toStatementString(),
					columns.values().toArray());

			return results;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("finally")
	public <T> List<T> getSingleValList(String sql, Object... params) {
		ResultSet rest = null;

		List<T> valueList = new ArrayList<T>();

		try {
			pstmt = getConnection().prepareStatement(sql);
			addParams(pstmt, params);
			System.out.println(sql);
			rest = pstmt.executeQuery();
			while (rest.next()) {
				T t = (T) rest.getObject(1);
				valueList.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (status == Status.STATUS_ACTIVE)
				isCommitSuccessfully = false;
		} finally {
			reset();
			return valueList;
		}
	}

	@SuppressWarnings("finally")
	public <T> T getSingleValue(String sql, Object... params) {
		ResultSet rest = null;

		T t = null;

		try {
			pstmt = getConnection().prepareStatement(sql);
			addParams(pstmt, params);
			System.out.println(sql);
			rest = pstmt.executeQuery();
			if (rest.next()) {
				t = (T) rest.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (status == Status.STATUS_ACTIVE)
				isCommitSuccessfully = false;
		} finally {
			reset();
			return t;
		}
	}

	public boolean modify(Object obj) {
		Class clazz = obj.getClass();
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		Update update = new Update();
		update.setTableName(getWapperQuote(table.getName()));

		try {

			Map<String, Object> primaryKeyColumnsMap = getprimaryKeyColumns(obj);

			String[] primaryColumnNames = primaryKeyColumnsMap.keySet()
					.toArray(StringUtil.STRINGS_MODE);
			update.setPrimaryKeyColumnNames(StringUtil.superaddition(
					openQuote(), primaryColumnNames, closeQuote()));

			Map<String, Object> columns = getNotNullColumns(obj, false);
			if (columns.isEmpty()) {
				throw new RuntimeException("要修改的实体类中除主键外没有任何字段被赋值！");
			}

			update.addColumns(StringUtil.superaddition(openQuote(), columns
					.keySet().toArray(StringUtil.STRINGS_MODE), closeQuote()));

			List<Object> params = new ArrayList<Object>();
			params.addAll(columns.values());
			params.addAll(primaryKeyColumnsMap.values());

			return execute(update.toStatementString(), params.toArray()) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean add(Object obj) {
		Class clazz = obj.getClass();
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		List<Object> params = new ArrayList<Object>();
		try {

			Insert insert = new Insert();
			insert.setTableName(getWapperQuote(table.getName()));

			Map<String, Object> noPrimaryColumnsMap = getNotNullColumns(obj,
					false);

			insert.addColumns(StringUtil.superaddition(openQuote(),
					noPrimaryColumnsMap.keySet().toArray(
							StringUtil.STRINGS_MODE), closeQuote()));
			params.addAll(noPrimaryColumnsMap.values());

			return execute(insert.toStatementString(), params.toArray()) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean delete(Object obj) {

		Class clazz = obj.getClass();
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());

		try {
			Map<String, Object> primaryKeyColumns = getprimaryKeyColumns(obj);
			if (primaryKeyColumns.size()==0){ 
				throw new RuntimeException("要删除的实体中没有任何的主键包含值");
			}
			Delete delete = new Delete().setTableName(getWapperQuote(table.getName()));
			
				delete.setPrimaryKeyColumnNames(
						StringUtil.superaddition(openQuote(),
								primaryKeyColumns.keySet().toArray(
										StringUtil.STRINGS_MODE),
								closeQuote()));
			
					
			return execute(delete.toStatementString(), primaryKeyColumns
					.values().toArray()) > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除对象通过对象ID
	 * @param obj
	 * @return 操作是否成功
	 */
	public boolean deleteByExample(Object obj){
		Class clazz = obj.getClass();
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());

		try {
			
			Delete delete = new Delete().setTableName(getWapperQuote(table.getName()));
			Map<String, Object> columns = getNotNullColumns(obj, false);
			
			Where where = new Where();
			for (Entry<String, Object> entry : columns.entrySet()) {
				where.eq(getWapperQuote(entry.getKey()), entry.getValue());
			}
			if (!where.isEmpty()) {
				delete.setWhere(where.getWhereClause());
			}
			
			return execute(delete.toStatementString(), where.getParams()
					.toArray()) > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean exist(Object objExample) {
		return !getObjListByExample(objExample).isEmpty();
	}

	@SuppressWarnings("finally")
	public int[] execTransaction(LinkedHashMap<String, Object[]> commands) {
		Connection conn = null;
		int[] results = new int[commands.size()];

		try {
			conn = getConnection();
			conn.setAutoCommit(false); // 设置自动提交为false

			int flag = 0;
			for (Entry<String, Object[]> command : commands.entrySet()) {
				pstmt = conn.prepareStatement(command.getKey()); // 添加SQL命令

				if (command.getValue() != null)
					for (int i = 0; i < command.getValue().length; i++) {
						pstmt.setObject(i + 1, command.getValue()[i]); // 添加每一个SQL命令执行所需要的参数
					}
				results[flag++] = pstmt.executeUpdate();
			}

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			reset();
			return results;
		}
	}

	@SuppressWarnings("finally")
	public boolean executeTransaction(LinkedHashMap<String, Object[]> commands) {
		Connection conn = null;
		boolean success = false;

		try {
			conn = getConnection();
			conn.setAutoCommit(false); // 设置自动提交为false

			for (Entry<String, Object[]> command : commands.entrySet()) {
				pstmt = conn.prepareStatement(command.getKey()); // 添加SQL命令
				if (command.getValue() != null)
					for (int i = 0; i < command.getValue().length; i++) {
						pstmt.setObject(i + 1, command.getValue()[i]); // 添加每一个SQL命令执行所需要的参数
					}

				pstmt.executeUpdate();
			}

			conn.commit();
			conn.setAutoCommit(true);
			success = true;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			reset();
			return success;
		}
	}

	public void beginTransaction() {
		try {
			getConnection().setAutoCommit(false);
			status = Status.STATUS_ACTIVE;
		} catch (SQLException e) {
			e.printStackTrace();
			status = Status.STATUS_UNKNOWN;
		}
	}

	public void commitTransaction() {
		try {
			if (isCommitSuccessfully) {
				conn.commit();
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(conn);
			this.status = Status.STATUS_UNKNOWN;
		}
	}

	public void rollbackTransaction() {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(conn);
			this.status = Status.STATUS_UNKNOWN;
		}
	}
	
	

	protected char closeQuote() {

		return '"';
	}

	protected char openQuote() {

		return '"';
	}
	
	/**
	 * 获取包装后的数据方言字段
	 * @param target 要包装的内容
	 * @return
	 */
	protected String getWapperQuote(String target){
		return openQuote()+target+closeQuote();
	}

	protected void addParams(PreparedStatement pstmt, Object... params)
			throws SQLException {

		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
	}

}
