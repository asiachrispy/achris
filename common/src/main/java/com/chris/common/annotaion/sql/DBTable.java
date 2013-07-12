package com.chris.common.annotaion.sql;

import java.lang.annotation.*;

/**
 * User: chris
 * Date: 12-9-8
 * Time: 下午2:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    public String name() default ""; // 如果没有设置则会使用类名作为表名
    public String engine() default "InnoDB";
    public String charset() default "utf8";
    //ENGINE=InnoDB DEFAULT CHARSET=utf8 
}
