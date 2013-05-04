
package com.zt.utils.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��ʶĿ��Ϊһ�����ݱ�
 * @author zhoutao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table
{

    public abstract String name();

    public abstract String catalog();

    public abstract String schema();

    public abstract UniqueConstraint[] uniqueConstraints();
}
