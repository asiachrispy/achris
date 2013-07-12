package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午2:18
 */
public class Entry <T extends Comparable<T>> {

    private T item;

    public Entry() {
    }

    public Entry(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public int compareTo(Entry<T> oo) {
        return item.compareTo(oo.item);
    }
}
