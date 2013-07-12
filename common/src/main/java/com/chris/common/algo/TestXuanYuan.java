package com.chris.common.algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TestXuanYuan {


    // 查找一个整型数组的平衡点.
    public static int equity(int[] numbers) {
        int total = 0;

        for (int i = 0; i < numbers.length; i++) {
            total += numbers[i];
        }

        int frontSum = 0;

        for (int i = 0; i < numbers.length; i++) {
            int backSum = total - frontSum - numbers[i];
            System.out.println(i + "------" + frontSum);
            System.out.println(i + "------" + backSum);
            if (frontSum == backSum) {
                return i;
            }
            frontSum += numbers[i];
        }

        return -1;
    }

    // 寻找支配点
    public static int getZhiPeiDian(int[] temp) {
        int size = temp.length;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Integer x : temp) {
            Integer freq = map.get(x);
            map.put(x, (freq == null ? 1 : freq + 1));
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer eachCount = entry.getValue();

            if (eachCount.intValue() > (size / 2))
                return entry.getKey();
        }

        throw new RuntimeException("Can not find zhiPeiDian");
    }

    // 实现一个整型数组类似java.lang.String的charAt方法.
    public static int intAt(int a[], int index) throws Exception {

        if (index >= 0 && index < a.length) {
            return a[index];
        } else {
            throw new Exception("Argument Error");
        }
    }

    // 实现一个去除整型数组中绝对值相同的数字.最后返回一个长度.
    public static int getDiffernt(int arr[]) {
        Set<Integer> set = new TreeSet<Integer>();
        for (int i = 0; i < arr.length; i++) {
            set.add(Math.abs(arr[i]));
        }
        for (Integer x : set) {
            System.out.print(x);
        }
        return set.size();
    }

    /*
      * 对一个数组进行排序，利用TreeSet
      * 输入参数：数组
      * 输出参数：Set
      */
    public Set<Integer> sort(int c[]) {
        TreeSet<Integer> tSet = new TreeSet<Integer>();
        for (int j = 0; j < c.length; j++) {
            tSet.add(c[j]);
        }
        return tSet;
    }

    /*
      * 找一个数在数组中的索引（下标）
      * 输入参数：数组，要找的数字
      * 输出参数：对应的下标
      */
    public int findIndex(int a[], int b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b) {
                return i;
            }
        }
        return -1;
    }

    /*
      * 找一个数在数组中的索引（下标）
      * 输入参数：数组，要找的数字
      * 输出参数：对应的下标
      */
    public static int sum(int a) {
        int all = 0;
        if (a < 0)
            return 0;
        if (a == 0)
            return 0;
        if (a == 1)
            return 1;

        for (int i = 0; i < a; i++) {
            all = sum(i - 1) + sum(i - 2);
        }
        System.out.println("all:" + all);
        return all;
    }


    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        int[] numbers = {1, 3, 5, 7, 8, 25, 4, 20};
        int[] numbers2 = {2, 2, 2, 2, 2, 2, 2, 2, 2, 6, 6, 6, 7, 7, 8};
        int[] numbers3 = {2, -2, -3, -4, 3, 4, 5, 6, -7, 8};
        int[] numbers4 = {2, 1, 3, 7, 6, 5, 4};
        TestXuanYuan t = new TestXuanYuan();

        //1
        //int a=t.equity(numbers);
        //System.out.print(a);

        //2
        // int b=t.getZhiPeiDian(numbers2);
        // System.out.print(b);

        //3
        //int size = t.getDiffernt(numbers3);
        //System.out.print(size);

        //4
        //测试排序后数字在原数组中的索引
        //Set<Integer> tSet=new TreeSet<Integer>();
        //tSet=t.sort(numbers4);

//		for(Integer x:tSet){
//			System.out.println(x+"原数组中的序号为"+t.findIndex(numbers4, x)+".");
//		}

        t.sum(2);
    }
}
