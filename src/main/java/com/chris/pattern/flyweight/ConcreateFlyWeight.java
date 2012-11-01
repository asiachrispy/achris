package com.chris.pattern.flyweight;

/**
 * User: chris
 * Date: 12-11-1
 * Time: 下午4:15
 */
public class ConcreateFlyWeight extends FlyWeight {
    private Object key;

    public ConcreateFlyWeight(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    @Override
    public void operation() {
        System.out.println("key:" + key);
    }
}
