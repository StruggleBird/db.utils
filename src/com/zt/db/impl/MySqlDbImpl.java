package com.zt.db.impl;

import java.util.List;

import com.zt.db.AbstractDbUtil;
import com.zt.db.mapping.Catalog;
import com.zt.db.mapping.Table;
import com.zt.db.sql.Select;
import com.zt.util.StringUtil;


/**
 * MySql数据库操作实现类
 * @author zt
 * @since 2010-3-6
 */
public class MySqlDbImpl extends AbstractDbUtil{

	
	public <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex,
			String where,String order) {
		if (pageSize==0) { 	return null; }
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		Select select = new Select();
		String selectClause = StringUtil.join(",",openQuote(), table.getColumnNames().toArray(StringUtil.STRINGS_MODE),closeQuote());
		select.setSelectClause(selectClause).setFromClause(openQuote()+table.getName()+closeQuote());
		
		StringBuilder sbWhere = new StringBuilder();
		if (!StringUtil.IsNullOrEmpty(where)) {
			sbWhere.append(where);
		}else {
			sbWhere.append("1=1");
		}
		
		sbWhere.append(" limit ").append(pageIndex*pageSize).append(",").append(pageSize);
		select.setWhereClause(sbWhere.toString());
		
		if (!StringUtil.IsNullOrEmpty(order)) {
			select.setOrderByClause(order);
		}
		
		return getObjList(clazz, select.toStatementString());
		
	}
	
	@Override
	public char closeQuote() {
		
		return '`';
	}

	@Override
	public char openQuote() {
		
		return '`';
	}

	


}
