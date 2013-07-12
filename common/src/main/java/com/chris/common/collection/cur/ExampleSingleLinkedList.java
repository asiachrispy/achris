package com.chris.common.collection.cur;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */

/**
 * 顺序表的使用实例，ExampleSequentialList.java
 */

public class ExampleSingleLinkedList {
    public static void main(String args[]) throws Exception {

        SingleLinkedList sList = new SingleLinkedList();
        Entry<Integer> e1;

        for (int i = 0; i < 10; i++) {
            e1 = new Entry<Integer>(i);
            sList.append(e1);
        }
        sList.print();// 打印所有元素

        // 将当前位置向后移三位
        sList.next();
        sList.next();
        sList.next();
        System.out.println("当前位置的元素项：" + sList.getEntry().getItem());
        //删除两个元素
        sList.remove();
        sList.remove();
        System.out.println("删除两个元素后顺序表中共有" + sList.size() + "项：");
        sList.print();//打印所有元素
        System.out.println("当前位置的元素项：" + sList.getEntry().getItem());

        //在当前位置再插入两个元素
        sList.add(new Entry<Double>(1.11));
        sList.print();

        sList.add(new Entry<String>("java"));
        sList.print();

        sList.clear();
        sList.print();
    }

}