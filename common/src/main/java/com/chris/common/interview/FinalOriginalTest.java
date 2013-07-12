package com.chris.common.interview;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 上午11:37
 */
public class FinalOriginalTest {
    private final int a;
    private String name;

    public FinalOriginalTest() {
        a = 3;
    }

    public FinalOriginalTest(String name) {
        a = 3;
        this.name = name;
    }

    public static void main(String[] args) {
        FinalOriginalTest ft = new FinalOriginalTest();
        FinalOriginalTest ft1 = new FinalOriginalTest("hello");
    }

    class Test1 {
        private static final int a = 3;
        private String name;

        public Test1() {
        }

        public Test1(String name) {
            this.name = name;
        }

        public void main(String[] args) {
            Test1 ft = new Test1();
            Test1 ft1 = new Test1("hello");
        }
    }
}


/*
问题1:上面的程序能否编译通过？如果不能，请说明理由。
解答:不能编译通过，可能没有初始化变量a。因为对于final类型的成员变量的初始化，在构造方法中完成赋值，
如果一个类有多个构造方法，就要保证在每个构造方法中都要完成对该final类型变量的初始化工作。
所以需要在public FinalOriginalTest(String name)构造方法中给a赋值。

注意：final可以用在类、方法、变量上。
     1、final用在类上，表明当前类它不能被继承，没有子类。
     2、final用在方法上，表明当前方法不能被override，不能被重写。
     3、final用在变量上，表明当前变量是一个终态的变量，是一个常量，这个变量的值一但被赋值后就不能被改变了。
     对于final类型的成员变量的初始化方式:
     1、声明变量时直接赋值
     2、在构造方法中完成赋值，如果一个类有多个构造方法，就要保证在每个构造方法中都要完成对该final类型变量的初始化工作。

问题2:上面的程序中，修正问题1之后，将private final int a;改为private static final int a;能否编译通过？如果不能，请说明理由。
解答:不能编译通过，因为a是静态变量，在这个类还没有实例化的时候，它的值就已经有了。
所以对于一个int类型的static final类型的变量a来说，我们只能在声明的时候就给它赋值private static final int a = 3;
然后把构造方法里面的赋值给注释掉，这样编译就能通过了。

总结：对于final类型的变量，对于不加static我们可以有两种方式给它赋值:声明变量时直接赋值;在构造方法中完成赋值，
如果一个类有多个构造方法，就要保证在每个构造方法中都要完成对该final类型变量的初始化工作。
对于一个变量既是final又是static的，我们必须在声明变量时直接赋值。
*/