package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-24
 * Time: 下午12:01
 */

/**
 * 双链表节点的数据类型，包括节点数数据部分、指向另一个节点的多项
 */
public class DoubleNode {
    private Entry entry;    //私有的元素项
    private DoubleNode next;    //私有的指向下一个节点的指针
    private DoubleNode prev;    //私有的指向前一个节点的指针

    public DoubleNode() {//默认的构造函数
        this.entry = null;
        this.next = null;
        this.prev = null;
    }

    public DoubleNode( DoubleNode prev, Entry entry, DoubleNode next) {//构造函数
        this.entry = entry;
        this.next = next;
        this.prev = prev;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public DoubleNode next() {        // 获取下一个节点
        return next;
    }

    public DoubleNode prev() {        //获取前一个节点
        return prev;
    }

    public void setNext(DoubleNode new_next) {//重新设定下一个节点
        this.next = new_next;
    }

    public void setPrev(DoubleNode new_prev) {//重新设定前一个结点
        this.prev = new_prev;
    }
}
