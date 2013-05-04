
package com.zt.utils.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事物注释
 * @author zt
 * @since 2010-3-11
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transaction
{

    /**
     * 获取自定义事物级别
     * @return 下面级别中的任意一种
     * <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>, 
     * <code>Connection.TRANSACTION_READ_COMMITTED</code>,
     * <code>Connection.TRANSACTION_REPEATABLE_READ</code>, 
     * <code>Connection.TRANSACTION_SERIALIZABLE</code>, 
     * <code>Connection.TRANSACTION_NONE</code>.
     */
    public abstract int level();

}
