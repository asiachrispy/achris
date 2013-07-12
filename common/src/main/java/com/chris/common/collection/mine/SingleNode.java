package com.chris.common.collection.mine;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:20
 */

/**
 * 单链表节点的数据类型，包括节点数数据部分、指向另一个节点的指向
 * <p/>
 * SingleNode.java
 */

public class SingleNode {

    private ElemItem elem;    //私有的元素项
    private SingleNode next;    //私有的指向下一个节点的指针

    public SingleNode(ElemItem elem, SingleNode next) {//构造函数
        this.elem = elem;
        this.next = next;
    }

    public SingleNode() {//默认的构造函数
        this.elem = null;
        this.next = null;
    }

    public ElemItem getElem() {//获取元素项
        return elem;
    }

    public void setElem(ElemItem new_elem) {//重新设置元素项
        this.elem = new_elem;
    }

    public SingleNode getNext() {        // 获取下一个节点
        return next;
    }

    public void setNext(SingleNode new_next) {//重新设定下一个节点
        this.next = new_next;
    }

}