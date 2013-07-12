package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:17
 */
 //线性表（Linear List）的接口
interface List {

    public abstract void add(Entry entry) throws Exception;    //在当前位置插入元素

    public abstract Entry remove();            //删除并返回当前元素

    public abstract void append(Entry entry) throws Exception;    //在当前线性表尾部添加元素

    public abstract void clear() throws Exception;                //清空整个线性链表

//    public abstract void goFirst();                //将当前位置赋值为第一个位置

    public abstract int next() throws Exception;                    //设置当前位置为下一位置并返回位置

//    public abstract int prev();                    //设置当前位置为前一位置并返回位置

    public abstract void setEntry(Entry entry);    //设置当前位置的元素项

    public abstract Entry getEntry();        //获取当前位置的元素项

    public abstract int size();                //获取当前线性表的有效元素个数

    public abstract int capacity();            //获取线性表的最大尺寸

    public abstract boolean hasNext();             //当前位置是否在链表中

    public abstract void print();            //打印当前线性表中的有效元素

}