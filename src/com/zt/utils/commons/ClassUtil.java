
package com.zt.utils.commons;

/**
 * 类
 * @author zt
 * @since 2010-3-21
 */
public class ClassUtil
{

    public static <T> T getInstance(Class<T> clazz)
    {
        try
        {
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
