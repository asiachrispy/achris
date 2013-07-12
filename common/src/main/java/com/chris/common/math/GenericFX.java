package com.chris.common.math;

/**
 * 泛型是JDK
 * 5.0后出现新概念，泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。这种参数类型可以用在类、接口和方法的创建中，分别称为泛型类
 * 、泛型接口、泛型方法。
 * <p/>
 * 泛型类引入的好处不仅在于减少代码量，还在于一提供了编译时期数据类型的检查功能，可以提前预知错误的发生，增加代码安全性，二是减少了强制类型转换。
 * 下面定义一个泛型类
 *
 * @param <E>
 * @author asiachris
 */
public class GenericFX<E> {

    private E fx;

    public void setFX(E fx) {
        this.fx = fx;
    }

    public E getFX(){
        return fx;
    }

}
