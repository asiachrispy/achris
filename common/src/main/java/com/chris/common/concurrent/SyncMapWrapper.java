package com.chris.common.concurrent;

import java.util.HashMap;
import java.util.Map;

public class SyncMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;


    public SyncMapWrapper() {
        map = new HashMap<Object, Object>();
    }

    @Override
    public synchronized void clear() {
        map.clear();
    }

    @Override
    public synchronized Object get(final Object key) {
        return map.get(key);
    }

    @Override
    public synchronized void put(final Object key, final Object value) {
        map.put(key, value);
    }

    @Override
    public String getName() {
        return "synclock";
    }
}

