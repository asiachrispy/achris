package com.chris.common.algo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class JavaAlgorithms {
    /**
     * 打印九九乘法口诀表
     */
    public static void nineTable() {
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
     * 将某个日期以固定格式转化成字符串 2007-3-22 20:23:22
     *
     * @param date
     * @return str
     */
    public String date2FormatStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 判断任意一个整数是否素数
     *
     * @param num
     * @return boolean
     */
    public boolean isPrimeNumber(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
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
    public int binarySearch(int[] dataset, int data, int beginIndex, int endIndex) {
        int midIndex = (beginIndex + endIndex) / 2;
        // 如果查找的数要比开始索引的数据要小或者是比结束索引的书要大，或者开始查找的索引值大于结束的索引值返回-1没有查到
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
    public int binarySearch(int[] dataset, int data) {
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

        //nineTable();

        //System.getProperty();
        Properties ps = System.getProperties();
        for (int i = 0, count = ps.size(); i < count; i++) {
            System.out.println(i + ":" + ps.toString());
        }
    }
}
