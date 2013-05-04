
package com.zt.utils.db;

import com.zt.utils.commons.StringUtil;

public class DbUtilFactory
{

    private static DbUtil dbUtil = null;

    public synchronized static DbUtil createDbUtilInstance() throws Exception
    {
        if (dbUtil == null)
        {
            String dbUtilImplClassName = DbConfigUtil.getInstance().getProperty("dbUtilImplClassName");

            if (StringUtil.IsNullOrEmpty(dbUtilImplClassName))
            {
                throw new RuntimeException("没有配置可以提供的数据库操作实现类！");
            }

            try
            {
                Class<?> dbUtilClass = Class.forName(dbUtilImplClassName);
                dbUtil = (DbUtil)dbUtilClass.newInstance();
            }
            catch (Exception e)
            {
                throw new Exception("can not make the new instance for class [" + dbUtilImplClassName + "]" + e);
            }
        }
        return dbUtil;
    }
}
