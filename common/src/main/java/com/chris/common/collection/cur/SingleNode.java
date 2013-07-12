package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:20
 */

/**
 * 单链表节点的数据类型，包括节点数数据部分、指向另一个节点的指向
 * 单链表节点
根据上面的讨论，若链表中的节点只含有指向下一个节点的指针则成为单链表，其节点成为单链表节点(singly linked node)。
根据这一性质我们可以在单链表节点类中设计指向下一个节点的“后向指针”。当然，单链表节点包含存放具体数据的元素项，如果元素项为空，则该节点成为“哑节点”。
 * <p/>
 * SingleNode.java
 */

public class SingleNode {

    private Entry entry;    //私有的元素项
    private SingleNode next;    //私有的指向下一个节点的指针

    public SingleNode(Entry entry, SingleNode next) {//构造函数
        this.entry = entry;
        this.next = next;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public SingleNode() {//默认的构造函数
        this.entry = null;
        this.next = null;
    }

    public SingleNode next() {        // 获取下一个节点
        return next;
    }

    public void setNext(SingleNode new_next) {//重新设定下一个节点
        this.next = new_next;
    }

}