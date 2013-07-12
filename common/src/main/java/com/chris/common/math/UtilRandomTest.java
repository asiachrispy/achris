package com.chris.common.math;

import java.util.Random;

public class UtilRandomTest {
    public static void main(String[] args) {
        Random random = new Random();//创建random对象
        int intNumber = random.nextInt();//获取一个整型数

        int intNum = random.nextInt(10);//获取一个整型数

        float floatNumber = random.nextFloat();//获取一个浮点数(0-1)

        double doubleNumber = random.nextDouble();//获取双精度数(0-1)

        boolean booleanNumber = random.nextBoolean();//获取boolean数

        System.out.println("intNumber:" + intNumber);
        System.out.println("intNum:" + intNum);
        System.out.println("floatNumber:" + floatNumber);
        System.out.println("doubleNumber:" + doubleNumber);
        System.out.println("booleanNumber:" + booleanNumber);
    }
}
