package com.chris.common.interview;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 上午11:31
 */
public class ExceptionExecuteTest {
    public static void main(String[] args) {
        try {
            String s = null;
            System.exit(1);
            //return;
        } catch (RuntimeException e) {
            System.out.println("exception");
        } finally {
            System.out.println("finally");
        }
    }
}
/*
问题1:上面的程序能否编译通过？如果能，打印结果是什么？如果不能，请说明理由。
解答:能编译通过。打印结果是finally。

问题2:上面的程序将return;注释掉，添加语句System.exit(0);语句，能否编译通过？如果能，结果是什么？如果不能，请说明理由。
解答:能编译通过。结果是什么都不输出。分析:System.exit(0);表示程序正常退出，让虚拟机终止当前的程序。
当try中有System.exit(0);方法被调用的时候，它就不会去执行finally里面的内容，这是一个特例。所以这个程序不输出任何结果。

总结:异常的执行流程:在try语句中有返回return;如果后面有finally的话，那么它会在执行return之前，
程序跳到finally里面去执行，把finally里面的代码执行完之后在去执行return;方法返回。
*/