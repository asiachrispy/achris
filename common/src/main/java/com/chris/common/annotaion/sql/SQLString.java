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
public @interface SQLString {
    int length() default 45; // 字符长度
    String name() default "";
    boolean isFix() default false; // 是否是固定长度char类型
    Constraints constraints() default @Constraints;
}
