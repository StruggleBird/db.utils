
package com.zt.utils.db;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;

import com.zt.utils.db.annotation.Entity;
import com.zt.utils.db.mapping.Context;
import com.zt.utils.log.Log;
import com.zt.utils.log.LogFactory;

/**
 * dbutil 代理类
 * @author zhoutao
 *
 */
@Deprecated
public class DbUtilHandler implements InvocationHandler
{
    private static final Log log = LogFactory.getLog(DbUtilHandler.class);
    
    public DbUtilHandler(DbUtil dbUtil)
    {
        setDbUtil(dbUtil);
    }

    protected DbUtil dbUtil;

    public Object invoke(Object proxy, Method method, Object[] args)
    throws Throwable
    {
        log.info("before invoke...");
        if (args.length > 0)
        {
            Class<?> firstArgClass = args[0].getClass();
            //判断当前类是否为添加了Entity注解的类，以分辨是否为POJO
            if (firstArgClass.isAnnotation() && firstArgClass.isAnnotationPresent(Entity.class))
            {
                Context.registerTable(firstArgClass);
            }
        }
        Object result = method.invoke(dbUtil, args);
        return result;
    }

    public void setDbUtil(DbUtil dbUtil)
    {
        this.dbUtil = dbUtil;
    }

}
