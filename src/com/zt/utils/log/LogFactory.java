
package com.zt.utils.log;

/**
 * 日志工厂类
 * @author zhoutao
 *
 */
public class LogFactory
{

    private static final String log4JClass = "org.apache.log4j.Logger";

    public static Log getLog(Class<?> clazz)
    {
        return getLog(clazz.getName());
    }

    public static Log getLog(String className)
    {
        try
        {
            Class.forName(log4JClass);
            return new Log4JAdapter(className);
        }
        catch (Exception e)
        {
            return new SysLogAdapter();
        }
    }
}
