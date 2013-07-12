package com.chris.common.concurrent;

public interface MapWrapper {

    void put(Object key, Object value);

    Object get(Object key);

    void clear();

    String getName();
}
