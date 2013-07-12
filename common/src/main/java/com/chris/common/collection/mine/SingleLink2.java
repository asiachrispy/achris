package com.chris.common.collection.mine;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:19
 */


/**
 * 高效的链表类，在低效的链表类基础上做改进，SingleLink2.java
 */
public class SingleLink2 implements List {
    private SingleNode head;    //链表的表头
    private SingleNode tail;    //链表的表尾
    private SingleNode curr;    //链表的当前位置
    private int currSize;    // 当前的链表长度

    public SingleLink2() {    //构造函数
        head = new SingleNode(null, null);
        curr = head;// curr指向的是当前节点的前驱节点
        tail = head;
        currSize = 0;
    }

    /* (non-Javadoc)
    * @see List.List#insert(Element.ElemItem)
    */
    public void insert(ElemItem elem) {
        //用当前节点作为后驱节点、elem构造新的节点，并作为新的当前节点
        curr.setNext(new SingleNode(elem, curr.getNext()));
        if (curr == tail) {
            tail = tail.getNext();
        }
        currSize++;

    }

    public ElemItem remove() {
        //无删除对象
        if (curr == null || curr.getNext() == null) return null;
        //当前位置的元素项
        ElemItem forReturn = curr.getNext().getElem();
        if (curr.getNext() == tail)    //若要删除的是表尾，则特殊对待
            tail = curr;
        curr.setNext(curr.getNext().getNext());
        currSize--;
        return forReturn;
    }

    public void append(ElemItem elem) {
        tail.setNext(new SingleNode(elem, null));//在表尾添加元素项
        tail = tail.getNext();//将表尾位置向后移
        currSize++;

    }

    public void clear() {
        head = new SingleNode(null, null);    //将表头赋值为空节点
        curr = head;
        tail = head;    //还原为初始状态

    }

    public void goFirst() {
        curr = head;

    }

    public int next() {
        if (curr != null && curr.getNext() != null) curr = curr.getNext();
        return 0;
    }

    public int prev() {
        if (curr == null || curr == head) {
            return -1;
        } else {
            SingleNode pre = head;
            while (pre.getNext() != curr)
                pre = pre.getNext();
            curr = pre;
        }
        return 0;
    }

    public void setCurrVal(ElemItem elem) {
        if (curr != null && curr.getNext() != null)
            curr.getNext().setElem(elem);
    }

    public ElemItem getCurrVal() {
        if (curr != null && curr.getNext() != null)
            return curr.getNext().getElem();
        return null;
    }

    public int getSize() {
        return currSize;
    }

    public int getTotalSize() {
        return currSize;
    }

    public boolean inList() {
        return curr == null || curr.getNext() == null;
    }

    public void printList() {
        SingleNode ptr = head;
        while (ptr.getNext() != tail) {
            System.out.print(ptr.getNext().getElem().getElem()
                    + ", ");
            ptr = ptr.getNext();
        }
        System.out.println(tail.getElem().getElem() + ".");

    }

}
