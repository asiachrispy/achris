package com.chris.common.clazz;

import java.util.Map;

/**
 * User: chris
 * Date: 12-10-15
 * Time: 下午4:14
 */
public class ClassTest {

    public static void main(String[] args) {

        //jvmInfo();
        //classLoad();
        try {
        load();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void jvmInfo() {
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            System.out.println(entry.getKey()+"\t"+entry.getValue());
        }
    }

    public static void classLoad() {
        ClassTest  t = new ClassTest();
        Class c = t.getClass();
        ClassLoader loader = c.getClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }

    public static void load() throws ClassNotFoundException {
        ClassLoader loader = ClassTest.class.getClassLoader();
        System.out.println(loader);
//        使用ClassLoader.loadClass()来加载类，不会执行初始化块
        loader.loadClass("com.bplan.cls.Test");
        System.out.println(System.class.getClassLoader());
//        使用Class.forName()来加载类，默认会执行初始化块
        //Class.forName("Static");
//        使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        //Class.forName("Static", false, loader);
    }

    class S {
         {
            System.out.println("static");
        }
    }
}
