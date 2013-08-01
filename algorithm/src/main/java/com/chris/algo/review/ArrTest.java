package com.chris.algo.review;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-8-1
 */
public class ArrTest {

    public static void printArr(int[] arr) {
        if (validate(arr)) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 查找数组的平衡点，使得平衡点左边的数据之和等于平衡点右边数据之和
     * <p/>
     * 思路：
     * 1. 先求出数组的总和 sum
     * 2. 遍历所有数据，记录左边的数据总和left，并记录右边的数据之和right=sum-left
     * 3. 如果在位置index出，有left = right则Index就是平衡点
     * <p/>
     * 分析：
     */
    public static List<Integer> pinghengPoint(int[] arr) {
        if (arr == null && arr.length == 0) {
            return null;
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        List<Integer> index = new ArrayList<Integer>();
        int left = 0;
        int right = sum;
        for (int i = 0; i < arr.length; i++) {
            if (left == right - arr[i]) {
                index.add(i);
                System.out.println(i + " : " + arr[i]);
            }

            left += arr[i];
            right -= arr[i];
        }
        return index;
    }

    /*
    * 插入排序原理：
    * 在前i个数据中，从末尾位置开始进行两两比较排序;i++
    *
    * 时间复杂度：
    * 平均O(n2)，最坏情况O(n2)。
    */
    public static void insertSortAsc(int[] arr) {
        if (arr == null && arr.length == 0) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1); // 交换
                }
            }
        }
    }

    /*
    * 冒泡排序原理：
    * 在前N-i个数中，从开始位置开始进行两两比较排序;i++
    *
    * 时间复杂度：
    * 平均O(n2)，最坏情况O(n2)。
    */
    public static void bubbleSortAsc(int[] arr) {
        if (arr == null && arr.length == 0) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /*
    * 简选择排序原理：
    * 在后N-i个数中，选择最小的值和i进行交换
    *
    * 时间复杂度：
    * 平均O(n2)，最坏情况O(n2)
    */
    public static void selectSortAsc(int[] arr) {
        if (arr == null && arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];

            for (int j = i + 1; j < arr.length; j++) {// 在后面, 找最小的.
                if (arr[j] < min) {
                    minIndex = j;
                    min = arr[j];
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    /**
     * todo
     *
     * @param arr
     * @param low
     * @param hight
     */
    public static void quickSortAsc(int[] arr, int low, int hight) {
        if (low < hight) { // 这个条件用来结束递归
            int pre = low;
            int bac = hight;
            int value = arr[low];//任意

            while (pre < bac) {
                while (pre < bac && arr[bac] > value) {
                    bac--;// 从右向左找第一个小于x的数
                }

                if (pre < bac) {
                    arr[pre] = arr[bac];
                    pre++;
                }

                while (pre < bac && arr[pre] < value) {
                    pre++; // 从左向右找第一个大于x的数
                }

                if (pre < bac) {
                    arr[bac] = arr[pre];
                    bac--;
                }

                arr[pre] = value;

                quickSortAsc(arr, low, pre - 1);
                quickSortAsc(arr, pre + 1, hight);
            }
        }
    }

    /**
     * 二分查找特定整数在整型数组中的位置(递归)
     * <p/>
     * 条件：
     * 查找线性表必须是有序列表
     */
    public int binarySearch(int[] arr, int data, int beginIndex, int endIndex) {
        if (arr == null && arr.length == 0) {
            return -1;
        }

        //相当于mid = (low + high) / 2，但是效率会高些
        int midIndex = (beginIndex + endIndex) >>> 1;
        if (data < arr[beginIndex] || data > arr[endIndex] || beginIndex > endIndex)
            return -1;
        if (data < arr[midIndex]) {
            return binarySearch(arr, data, beginIndex, midIndex - 1);
        } else if (data > arr[midIndex]) {
            return binarySearch(arr, data, midIndex + 1, endIndex);
        } else {
            return midIndex;
        }
    }


    /**
     * 二分查找特定整数在整型数组中的位置(非递归)
     * <p/>
     * 条件：
     * 查找线性表必须是有序列表
     */
    public int binarySearch(int[] arr, int data) {
        if (arr == null && arr.length == 0) {
            return -1;
        }

        int beginIndex = 0;
        int endIndex = arr.length - 1;
        int midIndex = -1;

        if (data < arr[beginIndex] || data > arr[endIndex] || beginIndex > endIndex) {
            return -1;
        }

        while (beginIndex <= endIndex) {
            //相当于midIndex = (beginIndex + endIndex) / 2，但是效率会高些
            midIndex = (beginIndex + endIndex) >>> 1;
            if (data < arr[midIndex]) {
                endIndex = midIndex - 1;
            } else if (data > arr[midIndex]) {
                beginIndex = midIndex + 1;
            } else {
                return midIndex;
            }
        }
        return -1;
    }

    /*
    * Fibonacci数列 (递归)
    */
    public static int fibonacciR(int n) {
        if (0 == n) {
            return 0;
        } else if (1 == n) {
            return 1;
        } else {
            return fibonacciR(n - 1) + fibonacciR(n - 2);
        }
    }

    /*
    * Fibonacci数列 (非递归)
    */
    public static void fibonacci(int number) {
        int nLeft = 0;
        int nRight = 1;
        for (int i = 0; i < number; i++) {
            int sum = nLeft + nRight;
            System.out.println("sum:" + sum);
            nLeft = nRight;
            nRight = sum;
        }
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
     * 查找字符串中第一个只出现一次的字符
     * <p/>
     * 申请一个256大小的数组来统计每个字符出现的次数（以空间换时间），
     * 我们以原数组的元素值为下表，访问统计数组，直到遇到第一个访问数组元素值为1的元素，
     * 其所在位置的字符即为第一个只出现一次的字符。
     *
     * @param str
     */
    public static void onlyOne(String str) {
        int[] countArr = new int[256];
        char[] chars = str.toCharArray();
        for (char c : chars) {
            countArr[c]++;//c自动转为字符对应的整形值，会在数组为c的位置记录出现次数
        }

        for (char c : chars) {
            if (countArr[c] == 1) {//查找数组中c位置的数组，也就是出现的次数
                System.out.println(c);
            }
        }
    }

    /**
     * 将2个有序数合并成一个新的有序数组
     *
     * @param arra
     * @param arrb
     */
    public static int[] mergerSort(int[] arra, int[] arrb) {
        int a = 0, b = 0, n = 0;
        int[] ret = new int[arra.length + arrb.length];
        while (a < arra.length && b < arrb.length) {
            if (arra[a] < arrb[b]) {
                ret[n++] = arra[a];
                a++;
            } else if (arra[a] > arrb[b]) {
                ret[n++] = arrb[b];
                b++;
            } else {
                ret[n++] = arra[a];
                ret[n++] = arrb[b];
                a++;
                b++;
            }

            if (a == arra.length) {
                while (b < arrb.length) {
                    ret[n++] = arrb[b++];
                }
            }

            if (b == arrb.length) {
                while (a < arra.length) {
                    ret[n++] = arra[a++];
                }
            }
        }
        return ret;
    }

    /**
     * 将数组arra合并到数组arrb中
     *
     * @param arra 数组长度小的数组
     * @param arrb 数组长度大的数组，且能存放arra
     */
    public static void merger(int[] arrb, int jj, int[] arra, int ii) {
        int k = ii + jj - 1;//+ arrb.length - 1;
        int i = ii - 1;
        int j = jj - 1;
        while (i >= 0 && j >= 0) {
            if (arra[i] > arrb[j]) {
                arra[k--] = arra[i--];
            } else if (arra[i] < arrb[j]) {
                arra[k--] = arrb[j--];
            } else {
                arra[k--] = arra[i--];
                arra[k--] = arrb[j--];
            }
        }

        while (j >= 0) {
            arra[k--] = arrb[j--];
        }
    }

    public static void swap(int[] arr, int src, int dest) {
        int tmp = arr[dest];
        arr[dest] = arr[src];
        arr[src] = tmp;
    }

    private static boolean validate(int[] arr) {
        if (arr == null && arr.length == 0) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        int[] arr = {-7, 1, 5, 2, -4, 3, 0};
        //pinghengPoint(arr);

        //selectSortAsc(arr);
        //printArr(arr);

        //fibonacci(5);

        //onlyOne("abacd");

        int[] a = {1, 3, 6, 8, 9};
        int[] b = {2, 4, 6};
        printArr(a);
        printArr(b);
        int[] c = mergerSort(a, b);
        printArr(c);


        //--------------------
        int n = 3;
        int[] aa = {1, 3, 6, 8, 9};
        int[] bb = new int[aa.length + n];
        bb[0] = 2;
        bb[1] = 4;
        bb[2] = 6;
        printArr(aa);
        printArr(bb);

        merger(aa, aa.length, bb, n);

        printArr(bb);
    }

}
