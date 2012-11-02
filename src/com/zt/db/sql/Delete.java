package com.zt.db.sql;


import com.zt.util.StringUtil;

public class Delete {
	
	  private String tableName;
	
	  private String[] primaryKeyColumnNames;
	  
	  private String where;

	  
	  public Delete setTableName(String tableName) {
	    this.tableName = tableName;
	    return this;
	  }

	  public String toStatementString() {
	    StringBuffer buf = new StringBuffer(this.tableName.length() + 10);

	    buf.append("delete from ").append(this.tableName);
	    if ((this.where != null) || (this.primaryKeyColumnNames != null) )
	      buf.append(" where ");

	    boolean conditionsAppended = false;
	    if (this.primaryKeyColumnNames != null) {
	      buf.append(StringUtil.join("=? and ", this.primaryKeyColumnNames)).append("=?");
	      conditionsAppended = true;
	    }
	    if (this.where != null) {
	      if (conditionsAppended)
	        buf.append(" and ");

	      buf.append(this.where);
	      conditionsAppended = true;
	    }
	    return buf.toString();
	  }

	  public Delete setWhere(String where) {
	    this.where = where;
	    return this;
	  }

	  public Delete setPrimaryKeyColumnNames(String[] primaryKeyColumnNames) {
	    this.primaryKeyColumnNames = primaryKeyColumnNames;
	    return this;
	  }

}
