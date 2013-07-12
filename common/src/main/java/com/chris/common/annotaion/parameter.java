package com.chris.common.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * User: chris
 * Date: 12-9-8
 * Time: 下午5:16
 */
@Target(ElementType.PARAMETER)
public @interface parameter {
    String value() default "";
}
