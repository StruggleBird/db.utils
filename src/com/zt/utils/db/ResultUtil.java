
package com.zt.utils.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zt.utils.commons.TypeUtil;
import com.zt.utils.db.mapping.ColumnEntity;
import com.zt.utils.db.mapping.Context;
import com.zt.utils.db.mapping.TableEntity;
import com.zt.utils.log.Log;
import com.zt.utils.log.LogFactory;

/**
 * 
 * @author zt
 * @since 2009-09-28
 */
public class ResultUtil<T>
{
    protected static final Log log = LogFactory.getLog(ResultUtil.class);

    /**
     * 注入map值到对象中
     * 
     * @param cla
     *            要实例化的实体类
     * @param map
     *            存储实体对象相应属性的集合
     * @return 实体类对象
     */
    private static <T> T ImmitValue(Class<T> clazz, Map<String, Object> map) throws Exception
    {
        T t = null;

        t = clazz.newInstance();

        TableEntity table = Context.getTablesMap().get(clazz.getSimpleName());

        Map<String, ColumnEntity> columnsMap = table.getColumnsMap();
        Set<String> dbColumnNames = map.keySet();

        for (String columnName : dbColumnNames)
        { //遍历读取的列名称

            for (ColumnEntity column : columnsMap.values())
            {
                if (column.getName().equalsIgnoreCase(columnName))
                {
                    String fieldName = column.getFieldName();
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(t, TypeUtil.Change(map.get(columnName), field.getType()));
                }
            }
        }
        return t;
    }

    /**
     * 从SortedMap中获取实体集合
     * @param clazz 
     * @param maps 
     * @return 实体类列表List<Object>
     * @throws Exception 
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static <T> List<T> getList(Class<T> clazz, List<Map<String, Object>> dataList) throws Exception
    {
        List<T> objList = new ArrayList<T>();
        for (Map<String, Object> map : dataList)
        {
            T t = ResultUtil.ImmitValue(clazz, map);
            objList.add(t);
        }
        return objList;
    }

    /**
     * 将Result转型为实体类列表
     * @param clazz 实体类类型
     * @param result result对象
     * @return 实体类列表List<Object>
     * @throws Exception 
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> List<T> getList(Class<T> clazz, ResultSet rst) throws Exception
    {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>(0);
        while (rst.next())
        {
            ResultSetMetaData rsmd = rst.getMetaData();
            Map<String, Object> dataLine = new LinkedHashMap<String, Object>(0);
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                dataLine.put(rsmd.getColumnName(i), rst.getObject(i));
                //                System.out.println(rsmd.getColumnType(i) + "---" + rsmd.getColumnTypeName(i));
            }
            mapList.add(dataLine);
        }

        List<T> objList = null;
        objList = ResultUtil.getList(clazz, mapList);

        return objList;
    }

    public static Map<String, Object> toMap(ResultSet rs) throws SQLException
    {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        ResultSetMetaData rsmd = rs.getMetaData();
        if (rs.next())
        {
            int cols = rsmd.getColumnCount();
            for (int i = 1; i <= cols; i++)
                result.put(rsmd.getColumnName(i), rs.getObject(i));
        }
        return result;
    }

    public static <T> T GetObject(Class<T> clazz, ResultSet rst) throws Exception
    {
        T t = null;

        Map<String, Object> objMap = toMap(rst);
        if (objMap.size() == 0)
        {
            return null;
        }

        t = ResultUtil.ImmitValue(clazz, objMap);

        return t;

    }
}
