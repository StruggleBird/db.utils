
package com.zt.utils.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Î¨Ò»¼üÔ¼Êø
 * @author zhoutao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueConstraint
{

    public abstract String[] columnNames();
}
