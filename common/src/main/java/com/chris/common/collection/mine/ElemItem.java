package com.chris.common.collection.mine;
/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:18
 */

/**
 * 建立元素项类，这里采用Java泛型编程，并且泛型限制为
 * 继承了Comparable类的类型
 * 泛型T可以是任意类型，包括整数Integer，Double等
 */
public class ElemItem<T extends Comparable<T>> {
    public T elem;            //泛型元素

    public ElemItem() {        //默认构造函数
        elem = null;
    }

    public ElemItem(T t) {    //含参数的构造函数
        elem = t;
    }

    public T getElem() {        //获取元素
        return elem;
    }

    // 元素项之间具有可比较性
    public int compareTo(ElemItem<T> oo) {
        return elem.compareTo(oo.elem);
    }
}