package com.chris.algo.clazz;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 13-7-29
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */

import java.util.Scanner;

public class Literals {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        println("请选择您要操作项的编号：");
        println("1.十进制转二进制\t\t\t2.二进制转十进制");
        while (!input.hasNextInt()) {
            println("您输入的不是整型，请输入0-9的数字");
            input.nextLine();
        }
        Scanner input1 = new Scanner(System.in);
        switch (input.nextInt()) {
        case 1:
            println("\t\t\t\t您选择的是：1.十进制转二进制");
            println("请输入你要转换的二进制数:");
            int a = 0;
            a = input1.nextInt();
            println("您输入的十进制数是：" + a + " 转换成二进制是：" + convertBinary(a));
            break;
        case 2:
            println("\t\t\t\t您选择的是：2.二进制转十进制");
            println("请输入你要转换的二进制数:");
            String b = input1.nextLine();
            println("您输入的二进制数是：" + b + " 转换成十进制数是："
                    + convertAlgorism(b.toCharArray()));
            break;
        default:

            println("您输入的操作编号系统不能识别，系统将自动停止");
        }
        input.close();
    }

    // 十进制转换二进制
    private static String convertBinary(int sum) {
        StringBuffer binary = new StringBuffer();
        while (sum != 0 && sum != 1) {
            binary.insert(0, sum % 2);
            println("sum=" + sum + "余数=" + (sum % 2) + "除数=" + sum / 2);
            sum = sum / 2;
            if (sum == 0 || sum == 1) {
                binary.insert(0, sum % 2);
            }
        }
        return binary.toString();
    }

    // 二进制转十进制
    private static int convertAlgorism(char[] cars) {
        int result = 0;
        int num = 0;
        for (int i = cars.length - 1; 0 <= i; i--) {
            int temp = 2;
            if (num == 0) {
                temp = 1;
            } else if (num == 1) {
                temp = 2;
            } else {
                for (int j = 1; j < num; j++) {
                    temp = temp * 2;
                }
            }
            int sum = Integer.parseInt(String.valueOf(cars[i]));
            result = result + (sum * temp);
            num++;
        }

        return result;
    }

    private static void println(Object info) {
        System.out.println(info);
    }

    private static void print(Object info) {
        System.out.print(info);
    }
}
