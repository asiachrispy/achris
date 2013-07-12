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
public @interface SQLLong {
    int length() default 11;
    String name() default "";
    Constraints constraints() default @Constraints;
}
