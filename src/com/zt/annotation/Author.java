package com.zt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 描述类型 进行元数据的标识
 * @author zt
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Author {
	
	public abstract String name();
	
	autherType type() default autherType.wuxia ;
	
	public enum autherType{ wuxia,yanqing,qihuan };
	
}
