package com.chris.crawler.core.parser;

import java.lang.annotation.*;

/**
 * tag-name
 * tag-class/id
 * User: zhong.huang
 * Date: 13-5-29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Tag {

    public enum TYPE {HTML, TEXT}

    public String clazz() default "";

    public String id() default "";

    public TYPE tagType() default TYPE.HTML;

    public boolean required() default true;
}
