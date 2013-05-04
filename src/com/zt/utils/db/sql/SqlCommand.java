package com.zt.utils.db.sql;

import java.util.ArrayList;
import java.util.List;

import com.zt.utils.commons.StringUtil;

public class SqlCommand {
	
	public SqlCommand(){}
	
	public SqlCommand(String sql){
		if (StringUtil.IsNullOrEmpty(sql)) {
			return;
		}
		
		this.sbSql.append(sql);
	}
	
	private StringBuilder sbSql = new StringBuilder();
	
	private List<Object> params = new ArrayList<Object>();
	
	
	public SqlCommand addWhere(Where where)
	{
		if (StringUtil.IsNullOrEmpty(where.getWhereClause())) {
			return this;
		}
		sbSql.append(" ").append(where.getWhereClause());
		params.addAll(where.getParams());
		return this;
	}
	
	public SqlCommand addOrder(String order)
	{
		if (StringUtil.IsNullOrEmpty(order)) {
			return this;
		}
		sbSql.append(" ").append(order);
		return this;
	}
	
	public String getSqlCommand()
	{
		return this.sbSql.toString();
	}
	
	public List<Object> getParams()
	{
		return this.getParams();
	}
	
	
}
