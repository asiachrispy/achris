package com.chris.common.interview;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 上午11:33
 */
public class FinalReferenceTest {
    public static final StringBuffer sb = new StringBuffer();

    public static void main(String[] args) {
        sb.append("hello");
    }
}
/*
问题1:上面的程序能否编译通过？如果能，说明理由，如果不能，请说明理由。
解答:能编译通过。
分析:对于final修饰的引用类型，到底这个引用不能变还是这个引用指向的对象的内容不能变？答案是引用不能改变，
而引用指向的内容是可以改变的。

问题2:上面的程序在语句sb.append("hello");后面添加语句 sb = new StringBuffer()能否编译通过？如果能，说明理由，如果不能，请说明理由。
解答:不能编译通过。
分析:由于对于final修饰的引用类型，是引用不能改变，而引用指向的内容是可以改变的。因为sb已经指向了第一次new...产生的对象，
已经不能改变，所以在语句sb.append("hello");后面添加语句 sb = new StringBuffer()是不能编译通过的。

总结：对于final类型的引用变量来说，所谓的不能改变指的是该引用不能改变。
*/