
package com.zt.utils.db;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取工具类
 * @author zhoutao
 *
 */
public final class DbConfigUtil extends Properties
{

    /**
     * 
     */
    private static final long serialVersionUID = 6731588504301875011L;

    private static DbConfigUtil instance;

    public static DbConfigUtil getInstance()
    {

        if (instance != null)
        {
            return instance;
        }

        makeInstance();
        return instance;

    }

    private static synchronized void makeInstance()
    {
        if (instance == null)
        {
            instance = new DbConfigUtil();
        }
    }

    private DbConfigUtil()
    {
        InputStream iStream = getClass().getResourceAsStream("/db.properties"); //获取utils项目中的配置文件 已过时
        //InputStream iStream =System.getProperty("user.dir").getClass().getResourceAsStream("/db.properties");
        try
        {

            if (iStream == null)
            {
                throw new NullPointerException("the file db.properties is not found!");
            }

            load(iStream);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
