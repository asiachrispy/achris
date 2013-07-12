package com.chris.common.interview;

import java.lang.reflect.Field;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 上午11:45
 */
public class ReadOnlyClassByReflection {
    static class ReadOnlyClass {
        private String name = "hello";

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        ReadOnlyClass pt = new ReadOnlyClass();
        Class<?> clazz = ReadOnlyClass.class;
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(pt, "world");
        System.out.println(pt.getName());
    }
}

/*

总结：对于一个类，它只有唯一的一个Class对象，它来标识这个对象。这个Class对象就能够获得这个类的结构上的特征。那么通过class对象就可以来获得这个类相应的构造方法，属性等。
    获得某一个类它的class对象有4种方式:
    1、使用类的.class语法
    2、通过类的对象的getClass()方法。getClass()方法在Object类里面定义的。
    3、通过Class对象的forName()方法
    4、对于包装类，可以通过.TYPE语法方式
    通过类的反射机制，我们可以去改变只读的private的属性的值。
 */
