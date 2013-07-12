package com.chris.common.annotaion.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: chris
 * Date: 12-9-8
 * Time: 下午2:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    int length() default 11;   // 字段长度
    String name() default ""; // 字段名称，如果没有设置，则同标注的对象名称
    Constraints constraints() default @Constraints;
}
