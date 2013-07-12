package com.dajie.crawler.core.demo;

/**
 * User: zhong.huang
 * Date: 13-5-22
 */
public class Test {
    /**
     * Description
     *
     * @param args
     */
    public static void main(String[] args) {
//        Son s = new Son();
        Derived d = new Derived();
        System.out.println(d.whenAmISet);
    }

}

class Parent {

    {
        System.out.println("parent中的初始化块");
    }

    static {
        System.out.println("parent中static初始化块");
    }

    public Parent() {
        System.out.println("parent构造方法");
    }
}

class Son extends Parent {
    {
        System.out.println("son中的初始化块");
    }

    static {
        System.out.println("son中的static初始化块");
    }

    public Son() {
        System.out.println("son构造方法");
    }

}

class Base {
    Base() {
        System.out.println("base()");
        preProcess();
    }

    void preProcess() {
        System.out.println("base preProcess()");
    }
}

class Derived extends Base {
    public String whenAmISet = "set when declared";

    @Override
    void preProcess() {
        System.out.println(whenAmISet + " Derived preProcess()");
        whenAmISet = "set in preProcess()";
    }
}
/*
 当子类Derived 的构造函数被调用时，其会隐晦地调用其基类Base的构造函数（通过super()函数），于是基类Base的构造函数会调用preProcess() 函数，因为这个类的实例是Derived的，而且在子类Derived中对这个函数使用了override关键字，所以，实际上调用到的是：Derived.preProcess()，而这个方法设置了whenAmISet 成员变量的值为：“set in preProcess()”。
虽然上面的结论是错误的，但推导过程是合理的，只是不完整，下面是整个运行的流程：

    进入Derived 构造函数。
    Derived 成员变量的内存被分配。
    Base 构造函数被隐含调用。
    Base 构造函数调用preProcess()。
    Derived 的preProcess 设置whenAmISet 值为 “set in preProcess()”。
    Derived 的成员变量初始化被调用。
    执行Derived 构造函数体。
*/