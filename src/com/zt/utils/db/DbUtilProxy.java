
package com.zt.utils.db;

import java.lang.reflect.Method;

import com.zt.utils.db.annotation.Entity;
import com.zt.utils.db.mapping.Context;
import com.zt.utils.log.Log;
import com.zt.utils.log.LogFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * DbUtil ������
 * @author zhoutao
 *
 */
public class DbUtilProxy implements MethodInterceptor
{

    private static final Log log = LogFactory.getLog(DbUtilProxy.class);

    private DbUtil original;

    private static final DbUtilProxy dbUtilProxy = new DbUtilProxy();

    public DbUtilProxy()
    {
        try
        {
            original = DbUtilFactory.createDbUtilInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("initialize exception:" + e);
        }
    }

    public DbUtil getOriginal()
    {
        return original;
    }

    public static DbUtilProxy getInstance()
    {
        return dbUtilProxy;
    }

    public DbUtil proxy(DbUtil target)
    {
        this.original = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (DbUtil)enhancer.create();
    }

    /**
     * create a dbUtil instance
     * @return instance of dbUtil
     */
    public DbUtil createProxy()
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(original.getClass());
        enhancer.setCallback(this);
        return (DbUtil)enhancer.create();
    }

    /**
     * static create dbUtil method
     * @return instance of dbUtil
     */
    public static DbUtil create()
    {
        return getInstance().createProxy();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
    {
        log.debug("invoke the method [" + method.getName() + "] ");
        if (args.length > 0)
        {
            Object firstArg = args[0];
            if (firstArg != null)
            {
                //�жϵ�һ��������ǰ���Ƿ�Ϊ�����Entityע����࣬�Էֱ��Ƿ�ΪPOJO
                Class<?> objClass = null;
                if (firstArg instanceof Class)
                {
                    objClass = (Class<?>)firstArg;
                    if (objClass.isAnnotationPresent(Entity.class))
                    {
                        Context.registerTable(objClass);
                    }
                }
                else if (firstArg.getClass().isAnnotationPresent(Entity.class))
                {
                    objClass = firstArg.getClass();
                    if (objClass.isAnnotationPresent(Entity.class))
                    {
                        Context.registerTable(objClass);
                    }
                }
            }
        }
        Object result = proxy.invokeSuper(obj, args);
        return result;
    }
}
