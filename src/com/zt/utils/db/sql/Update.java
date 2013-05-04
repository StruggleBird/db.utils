
package com.zt.utils.db.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zt.utils.commons.StringUtil;

/**
 * 更新语句
 * @author zhoutao
 *
 */
public class Update
{

    private String tableName;

    private String[] primaryKeyColumnNames;

    private String where;

    private Map<String, String> columns = new LinkedHashMap<String, String>();

    private Map<String, String> whereColumns = new LinkedHashMap<String, String>();

    public String getTableName()
    {
        return this.tableName;
    }

    public Update setTableName(String tableName)
    {
        this.tableName = tableName;
        return this;
    }

    public Update setPrimaryKeyColumnNames(String[] primaryKeyColumnNames)
    {
        this.primaryKeyColumnNames = primaryKeyColumnNames;
        return this;
    }

    public Update addColumns(String[] columnNames)
    {
        for (int i = 0; i < columnNames.length; ++i)
            addColumn(columnNames[i]);

        return this;
    }

    public Update addColumns(String[] columnNames, boolean[] updateable)
    {
        for (int i = 0; i < columnNames.length; ++i)
            if (updateable[i] != false)
                addColumn(columnNames[i]);

        return this;
    }

    public Update addColumns(String[] columnNames, String value)
    {
        for (int i = 0; i < columnNames.length; ++i)
            addColumn(columnNames[i], value);

        return this;
    }

    public Update addColumn(String columnName)
    {
        return addColumn(columnName, "?");
    }

    public Update addColumn(String columnName, String value)
    {
        this.columns.put(columnName, value);
        return this;
    }

    public Update addWhereColumns(String[] columnNames)
    {
        for (int i = 0; i < columnNames.length; ++i)
            addWhereColumn(columnNames[i]);

        return this;
    }

    public Update addWhereColumns(String[] columnNames, String value)
    {
        for (int i = 0; i < columnNames.length; ++i)
            addWhereColumn(columnNames[i], value);

        return this;
    }

    public Update addWhereColumn(String columnName)
    {
        return addWhereColumn(columnName, "=?");
    }

    public Update addWhereColumn(String columnName, String value)
    {
        this.whereColumns.put(columnName, value);
        return this;
    }

    public Update setWhere(String where)
    {
        this.where = where;
        return this;
    }

    @SuppressWarnings("unchecked")
    public String toStatementString()
    {
        StringBuffer buf = new StringBuffer();

        buf.append("update ").append(this.tableName).append(" set ");
        @SuppressWarnings("unused")
        boolean assignmentsAppended = false;
        Iterator<?> iter = this.columns.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry e = (Map.Entry)iter.next();
            buf.append(e.getKey()).append('=').append(e.getValue());
            if (iter.hasNext())
                buf.append(", ");

            assignmentsAppended = true;
        }

        boolean conditionsAppended = false;
        if ((this.primaryKeyColumnNames != null) || (this.where != null) || (!(this.whereColumns.isEmpty())))
            buf.append(" where ");

        if (this.primaryKeyColumnNames != null)
        {
            buf.append(StringUtil.join("=? and ", this.primaryKeyColumnNames)).append("=?");
            conditionsAppended = true;
        }
        if (this.where != null)
        {
            if (conditionsAppended)
                buf.append(" and ");

            buf.append(this.where);
            conditionsAppended = true;
        }
        iter = this.whereColumns.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry e = (Map.Entry)iter.next();
            if (conditionsAppended)
                buf.append(" and ");

            buf.append(e.getKey()).append(e.getValue());
            conditionsAppended = true;
        }

        return buf.toString();
    }
}
