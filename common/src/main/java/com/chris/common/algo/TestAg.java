package com.chris.common.algo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAg {
    /**
     * 打印九九乘法口诀表
     */
    public static void nineNineMulitTable() {
        for (int i = 1, j = 1; j <= 9; i++) {
            System.out.print(i + "*" + j + "=" + i * j + " ");
            if (i == j) {
                i = 0;
                j++;
                System.out.println();
            }
        }
    }

    /**
     * 将某个日期以固定格式转化成字符串
     *
     * @param date
     * @return str
     */
    public static String date2FormatStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        System.out.println(str);
        return str;
    }


    /**
     * 获得任意一个整数的阶乘
     *
     * @param n
     * @returnn!
     */
    public static long factorial(int num) {
        //递归 
        if (num == 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }

    /**
     * 二分查找特定整数在整型数组中的位置(递归)
     *
     * @param dataset
     * @param data
     * @param beginIndex
     * @param endIndex
     * @return index
     */
    public static int binarySearch(int[] dataset, int data, int beginIndex, int endIndex) {
        int midIndex = (beginIndex + endIndex) / 2;
        //如果查找的数要比开始索引的数据要小或者是比结束索引的书要大，或者开始查找的索引值大于结束的索引值返回-1没有查到
        if (data < dataset[beginIndex] || data > dataset[endIndex] || beginIndex > endIndex) {
            return -1;
        }

        if (data < dataset[midIndex]) {
            return binarySearch(dataset, data, beginIndex, midIndex - 1);
        } else if (data > dataset[midIndex]) {
            return binarySearch(dataset, data, midIndex + 1, endIndex);
        } else {
            return midIndex;
        }
    }

    /**
     * 二分查找特定整数在整型数组中的位置(非递归)
     *
     * @param dataset
     * @param data
     * @return index
     */
    public static int binarySearch(int[] dataset, int data) {
        int beginIndex = 0;
        int endIndex = dataset.length - 1;
        int midIndex = -1;
        if (data < dataset[beginIndex] || data > dataset[endIndex] || beginIndex > endIndex) {
            return -1;
        }
        while (beginIndex <= endIndex) {
            midIndex = (beginIndex + endIndex) / 2;
            if (data < dataset[midIndex]) {
                endIndex = midIndex - 1;
            } else if (data > dataset[midIndex]) {
                beginIndex = midIndex + 1;
            } else {
                return midIndex;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //nineNineMulitTable();
        //date2FormatStr(new Date());
        //System.out.println(factorial(5));
        int[] arr = {9, 3, 5, 4, 2, 7, 6, 1, 0};
        //bubble(arr);
        //insert(arr);
        select(arr);
    }

    //随着计算，前面的数据不断有序
    public static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    swp(arr, j - 1, j);
                }
            }
        }
        printArr(arr);
    }

    public static void select(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int mind = i;
            int min = arr[i];

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    mind = j;
                }
            }

            if (mind != i) {
                swp(arr, i, mind);
            }
        }
        printArr(arr);
    }

    public static void bubble(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swp(arr, j, j + 1);
                }
            }
        }
        printArr(arr);
    }

    public static void printArr(int[] arr) {
        System.out.println("\n sort end:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void swp(int[] arr, int src, int dest) {
        int temp = arr[src];
        arr[src] = arr[dest];
        arr[dest] = temp;
    }
}


