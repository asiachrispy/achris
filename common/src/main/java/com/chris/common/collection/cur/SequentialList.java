package com.chris.common.collection.cur;

/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午2:08
 */
/*
顺序表(sequential list)实际上是利用数组和一些参数来实现线性表所定义的功能。在顺序表创建时，我们需要同时声明顺序表的最大长度，即这里的数组的长度。这里的数组与我们通常所说的数组实际上是完全相同的，其元素的是ElemItem类型。顺序表的表头即为数组的第一个元素，对当前指向的调整也就是数组下标的调整。此外，顺序表中所能容纳的元素的个数也被数组的长度限制着。
向当前位置插入新的元素项的操作也受着数组结构的制约。一方面，在数组中元素已经填满时顺序表中将不能再插入(insert)或添加(append)新的元素；另一方面，在插入和删除顺序表中当前元素时需要对数组进行移位操作从而保证元素的有序性和连贯性，如图。
可以发现，在进行插入、删除时操作时间开销将是O(n)的复杂度，其中n表示顺序表的长度，在下面的比较中将会发现，这这个开销是比较大的。
除了在上一节中线性表接口中设计的函数之外，我们设计的顺序表类还包括数组、当前元素指示curr和当前有效元素个数currSize这三个私有(private)参数，这些参数不能被类的成员以外的函数调用。
*/
// 顺序表(sequential list)
public class SequentialList implements List {

    private Entry[] entries;
    private int capacity;
    private int index;
    private int size;

    public SequentialList() {
        init(0);
    }

    public SequentialList(int capacity) {
        init(capacity);
    }

    private void init(int capacity) {
        this.size = 0;              //当前有效元素个数
        this.index = -1;             //当前位置为第一个
        this.capacity = capacity;  //顺序表的大小
        this.entries = new Entry[capacity];//创建空间
    }

    public void add(Entry entry) throws Exception {
        if (size > capacity) {
            throw new Exception("out of capacity");
        }

        // 将数组中的元素后移一个位置
        for (int i = size - 1; i >= index; i--) {
            entries[i + 1] = entries[i];
        }
        // 将新加元素放入表头
        entries[index] = entry;
        size++;
    }

    public Entry remove() {
        if (0 == size) {
            System.out.print("empty list.");
            return null;
        }
        // 首先提取当前元素
        Entry entry = entries[index];

        // 当前位置是表的尾部
        if (index == (size - 1)) {
            index--;
            size--;
            return entry;
        }

        for (int i = index; i < size - 1; i++) {
            entries[i] = entries[i + 1];
        }
        size--;
        // index--;    想想为什么这里不要
        return entry;
    }

    public void append(Entry entry) throws Exception {
        if (capacity == 0) {
            throw new Exception("empty list");
        }

        if (size > capacity) {
            throw new Exception("out of capacity");
        }
        entries[size++] = entry;
    }

    public void clear() throws Exception {
        if (capacity == 0) {
            throw new Exception("empty list");
        }
        size = 0;
        index = 0;
    }

    public int next() throws Exception {
        if (hasNext()) {
            return index++;//TODO
        }

        throw new Exception("out of list");
    }

    public void setEntry(Entry entry) {
        entries[index] = entry;
    }

    public Entry getEntry() {
        return entries[index];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean hasNext() {
        return (index < size);
    }

    public void print() {
        if (size == 0) {
            System.out.println("empty list.");
            return;
        }

        System.out.print("[");
        for (int i = 0; i < size - 1; i++) {
            System.out.print(entries[i].getItem() + ", ");
        }
        System.out.print(entries[size - 1].getItem() + "]");
        System.out.println();
    }
}