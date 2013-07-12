package com.chris.common.math;

public class MathRandomTest {
    public static void main(String[] args) {
        double i = Math.random();// random()会自动产生一个0.0-1.0的双精度随机数
        System.out.println(i);// 输出

        i = (Math.random() * 1000);// 产生0-1000的双精度随机数
        System.out.println(i);

        int b = (int) (Math.random() * 1000);// 产生0-1000的整数随机数
        System.out.println(b);

        System.out.println("round 11.5:" + Math.round(11.5));
    }
}