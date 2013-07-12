package com.chris.common.concurrent;

import java.util.Hashtable;
import java.util.Map;

//http://www.yuanma.org/data/2008/0506/article_3034.htm
//Hashtable和HashMap的区别
public class HashTableMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;


    public HashTableMapWrapper() {
        map = new Hashtable<Object, Object>();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object get(final Object key) {
        return map.get(key);
    }

    @Override
    public void put(final Object key, final Object value) {
        map.put(key, value);
    }

    @Override
    public String getName() {
        return "hashtable";
    }
}
