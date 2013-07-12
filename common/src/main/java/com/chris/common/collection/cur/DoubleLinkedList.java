package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-24
 * Time: 下午12:05
 */
public class DoubleLinkedList implements List {
    private DoubleNode next;
    private DoubleNode prev;
    private Entry entry;
    private int size;

    public DoubleLinkedList() {//默认的构造函数
        this.entry = null;
        this.next = null;
        this.prev = null;
    }

    public DoubleLinkedList(DoubleNode prev, Entry entry, DoubleNode next) {//构造函数
        this.entry = entry;
        this.next = next;
        this.prev = prev;
    }

    public void add(Entry entry) throws Exception {
        DoubleNode node = new DoubleNode(this.next.prev(), entry, this.next);
        this.next = node;
        this.next.next().setPrev(node);
    }

    public Entry remove() {
        prev = next.prev();
        return entry;
    }

    public void append(Entry entry) throws Exception {
        DoubleNode node = this.next;
        while (node.next() != null) {
             node = node.next();
        }

    }

    public void clear() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int next() throws Exception {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEntry(Entry entry) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Entry getEntry() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int capacity() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasNext() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
