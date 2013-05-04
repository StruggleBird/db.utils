
package com.zt.utils.db.mapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.zt.utils.commons.StringUtil;
import com.zt.utils.db.annotation.Column;
import com.zt.utils.db.annotation.Entity;
import com.zt.utils.db.annotation.Id;
import com.zt.utils.log.Log;
import com.zt.utils.log.LogFactory;

/**
 * 数据库映射 上下文 核心类
 * @author zt
 * @since 2010-3-7
 */
public class Context
{
    private static final Log log = LogFactory.getLog(Context.class);

    private static String name;

    private static Map<String, TableEntity> tablesMap = new HashMap<String, TableEntity>();

    /**
     * 添加实体类映射到数据目录中
     * @param clazz 实体类Class
     */
    public static synchronized void registerTable(Class<?> clazz)
    {
        if (Context.getTablesMap().containsKey(clazz.getSimpleName()))
        {
            return;
        }

        if (clazz.isAnnotationPresent(Entity.class))
        {
            Entity entity = (Entity)clazz.getAnnotation(Entity.class);
            String tableName = entity.value();

            if ("".equals(entity.value()))
            {
                tableName = clazz.getSimpleName();
            }

            TableEntity table = new TableEntity(tableName);

            Context.addTableMap(clazz.getSimpleName(), table);

            Field[] fields = clazz.getDeclaredFields();
            boolean hasPrimaryKey = false;
            for (Field field : fields)
            {
                if (field.isAnnotationPresent(com.zt.utils.db.annotation.Column.class))
                {
                    //获取字段所对应的数据库列名称
                    Column column = field.getAnnotation(Column.class);
                    boolean isPrimary = field.isAnnotationPresent(Id.class);

                    String columnName = "";
                    if (StringUtil.IsNullOrEmpty(column.value()))
                    {
                        columnName = field.getName();
                    }
                    else
                    {
                        columnName = column.value();
                    }

                    if (isPrimary)
                    {
                        hasPrimaryKey = true;
                    }

                    table.addColumnMap(field.getName(), new com.zt.utils.db.mapping.ColumnEntity(columnName, field
                        .getName(), isPrimary));
                }

            }

            if (!hasPrimaryKey)
            {
                throw new RuntimeException("没有在实体类主键字段上添加@Id或@Column标识！");
            }
            log.info("register entity[" + clazz.getSimpleName() + "] success");
        }
        else
        {
            throw new RuntimeException("没有在实体类声明时添加 @Entity标识！");
        }
    }

    /**
     * 添加表映射到库中
     * @param tName 
     * @param table
     */
    public static void addTableMap(String persistentClassName, TableEntity table)
    {
        if (tablesMap.containsKey(persistentClassName))
        {
            return;
        }

        tablesMap.put(persistentClassName, table);
    }

    public static String getName()
    {
        return name;
    }

    public static void setName(String name)
    {
        Context.name = name;
    }

    public static Map<String, TableEntity> getTablesMap()
    {
        return tablesMap;
    }

    public static void setTablesMap(Map<String, TableEntity> tablesMap)
    {
        Context.tablesMap = tablesMap;
    }

}
