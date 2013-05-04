
package com.zt.utils.db.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据表的映射类
 * @author zt
 * @since 2010-3-7
 */
public class TableEntity
{

    public TableEntity(String name)
    {
        this();
        this.name = name;
    }

    public TableEntity()
    {
        this.columnsMap = new HashMap<String, ColumnEntity>();
    }

    //数据表名称
    private String name;

    //数据表中列集合
    private Map<String, ColumnEntity> columnsMap;

    /**
     * 添加列到表中
     * @param fieldName 类字段名称
     * @param columnName 数据表中列名称
     */
    public void addColumnMap(String fieldName, ColumnEntity column)
    {
        if (columnsMap.containsKey(fieldName))
        {
            return;
        }

        columnsMap.put(fieldName, column);
    }

    public List<String> getPrimaryColumnNames()
    {
        List<String> primaryKeys = new ArrayList<String>();
        for (ColumnEntity column : columnsMap.values())
        {
            if (column.getIsPrimary())
            {
                primaryKeys.add(column.getName());
            }
        }
        return primaryKeys;
    }

    public List<String> getnoPrimaryColumnNames()
    {
        List<String> noPrimaryKeys = new ArrayList<String>();
        for (ColumnEntity column : columnsMap.values())
        {
            if (!column.getIsPrimary())
            {
                noPrimaryKeys.add(column.getName());
            }
        }
        return noPrimaryKeys;
    }

    public List<String> getColumnNames()
    {
        List<String> columns = new ArrayList<String>();
        for (ColumnEntity column : columnsMap.values())
        {
            columns.add(column.getName());
        }
        return columns;
    }

    public Map<String, ColumnEntity> getColumnsMap()
    {
        return columnsMap;
    }

    public void setColumnsMap(Map<String, ColumnEntity> columnsMap)
    {
        this.columnsMap = columnsMap;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
