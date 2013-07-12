package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:19
 */

/**
 * 高效的链表类，在低效的链表类基础上做改进
 */
public class SingleLinkedList implements List {
    private SingleNode head;    //链表的表头
    private SingleNode tail;    //链表的表尾
    private SingleNode index;   //定义为指向当前节点的下一个节点
    private int size;    // 当前的链表长度

    public SingleLinkedList() {
        this.head = new SingleNode(null, null);
        this.index = this.head;   // index指向的是当前节点的前驱节点
        this.tail = this.head;
        this.size = 0;
    }

    public void add(Entry entry) throws Exception {
        // 用当前节点的后驱节点作为新节点的后驱节点， 并用新节点作为当前节点的后驱节点
        index.setNext(new SingleNode(entry, index.next()));
        // 如果当前节点刚好是最后一个节点，则需要重新设置末尾节点
        if (index == tail) {
            tail = tail.next();
        }
        size++;
    }

    public Entry remove() {
        if (index == null || index.next() == null) {
            return null;
        }
        Entry entry = index.getEntry();
        if (index.next() == tail) {
            tail = index;
        }
        index.setNext(index.next().next());
        size--;
        return entry;
    }

    public void append(Entry entry) throws Exception {
        tail.setNext(new SingleNode(entry, null));//在表尾添加元素项
        tail = tail.next();//将表尾位置向后移
        size++;
    }

    public void clear() throws Exception {
        head = new SingleNode(null, null);    //将表头赋值为空节点
        index = head;
        tail = head;    //还原为初始状态
    }

    public int next() throws Exception {
        if (index != null && index.next() != null) {
            index = index.next();
        } else {
            System.out.println("no more node");
        }
        return 0;
    }

    public void setEntry(Entry entry) {
        if (index != null && index.next() != null) {
            index.next().setEntry(entry);
        }
    }

    public Entry getEntry() {
        if (index != null && index.next() != null) {
            return index.next().getEntry();
        }
        return null;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return size;
    }

    public boolean hasNext() {
        return index.next() != null;
    }

    public void print() {
        System.out.print("[");
        SingleNode ptr = head;
        while (ptr.next() != null) {
            System.out.print(ptr.next().getEntry().getItem() + ",");
            ptr = ptr.next();
        }
        System.out.println("]");
    }
}
