package com.zt.db.impl;

import java.util.List;

import com.zt.db.AbstractDbUtil;
import com.zt.db.mapping.Catalog;
import com.zt.db.mapping.Table;
import com.zt.db.sql.Select;
import com.zt.util.StringUtil;

/**
 * SqlServer数据库操作实现类 
 * @author zt
 * @since 2010-3-6
 */
public class SqlServerDbImpl extends AbstractDbUtil{

	
	public <T> List<T> getObjList(Class<T> clazz, int pageSize, int pageIndex,
			String where,String order) {
		if (pageSize==0) { 	return null; 	}
		
		Catalog.registerTable(clazz);
		Table table = Catalog.getTablesMap().get(clazz.getSimpleName());
		String pkName = table.getPrimaryColumnNames().get(0);
		
		Select select = new Select();
		
		String selectClause = "top "+pageSize+" "+ StringUtil.join(",",openQuote(), table.getColumnNames().toArray(StringUtil.STRINGS_MODE),closeQuote());
		select.setSelectClause(selectClause).setFromClause(openQuote()+table.getName()+closeQuote());
		
		StringBuilder sbWhere = new StringBuilder();
		if (!StringUtil.IsNullOrEmpty(where)) {
			sbWhere.append(where);
		}else {
			sbWhere.append("1=1");
		}
		
		if (pageIndex!=0) {
			sbWhere.append(" and ").append(openQuote()+ pkName+closeQuote()).append(" not in ")
			.append("(");
			Select subSelect = new Select();
			subSelect.setSelectClause("top "+pageSize*pageIndex+" "+ openQuote()+pkName+closeQuote()).setFromClause(openQuote()+table.getName()+closeQuote());
			
			if (!StringUtil.IsNullOrEmpty(where)) { subSelect.setWhereClause(where); }
			
			if (!StringUtil.IsNullOrEmpty(order)) { subSelect.setOrderByClause(order); 	}
			
			sbWhere.append(subSelect.toStatementString()).append(")");
			
		}
		select.setWhereClause(sbWhere.toString());
		if (!StringUtil.IsNullOrEmpty(order)) {
			select.setOrderByClause(order);
		}
		
		return getObjList(clazz, select.toStatementString());
		
	}
	
	@Override
	public char closeQuote() {
		
		return ']';
	}

	@Override
	public char openQuote() {
		
		return '[';
	}

	

}
