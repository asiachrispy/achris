package com.chris.common.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;

    public ConcurrentMapWrapper() {
        map = new ConcurrentHashMap<Object, Object>();
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
        return "concrrent";
    }

}
