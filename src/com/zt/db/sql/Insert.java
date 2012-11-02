package com.zt.db.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Insert {

	private String tableName;

	private Map<String, String> columns = new LinkedHashMap<String, String>();

	public Insert addColumn(String columnName) {
		return addColumn(columnName, "?");
	}

	public Insert addColumns(String[] columnNames) {
		for (int i = 0; i < columnNames.length; ++i)
			addColumn(columnNames[i]);
		return this;
	}

	public Insert addColumns(String[] columnNames, boolean[] insertable) {
		for (int i = 0; i < columnNames.length; ++i)
			if (insertable[i] != false)
				addColumn(columnNames[i]);

		return this;
	}

	public Insert addColumn(String columnName, String value) {
		this.columns.put(columnName, value);
		return this;
	}

	public Insert addIdentityColumn(String columnName) {
		String value = null;
		if (value != null)
			addColumn(columnName, value);
		return this;
	}

	public Insert setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public String toStatementString() {
		StringBuffer buf = new StringBuffer(this.columns.size() * 15
				+ this.tableName.length() + 10);
		buf.append("insert into ").append(this.tableName);

		buf.append(" (");

		Iterator<?> iter = this.columns.keySet().iterator();
		if(iter.hasNext()) buf.append(iter.next());
		do {
			if (!(iter.hasNext()))
				break;
			buf.append(",");
			buf.append(iter.next());
		} while (iter.hasNext());
		

		buf.append(") values (");
		iter = this.columns.values().iterator();
		if(iter.hasNext()) buf.append(iter.next());
		do {
			if (!(iter.hasNext()))
				break;
			buf.append(",");
			buf.append(iter.next());
		} while (iter.hasNext());
		

		buf.append(')');

		return buf.toString();
	}
}
