
package com.zt.utils.db;

import java.io.InputStream;
import java.util.Properties;

/**
 * �����ļ���ȡ������
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
        InputStream iStream = getClass().getResourceAsStream("/db.properties"); //��ȡutils��Ŀ�е������ļ� �ѹ�ʱ
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
