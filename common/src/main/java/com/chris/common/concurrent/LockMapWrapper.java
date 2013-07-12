package com.chris.common.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//http://xlongbuilder.javaeye.com/blog/417539
//（转）深入研究 ReentrantLock（重入锁）之引出话题篇
public class LockMapWrapper implements MapWrapper {
    private final Map<Object, Object> map;

    private final Lock lock;


    public LockMapWrapper() {
        map = new HashMap<Object, Object>();
        lock = new ReentrantLock();
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            map.clear();
        } catch (final Exception e) {
        } finally {
            lock.unlock();
        }

    }

    @Override
    public Object get(final Object key) {
        lock.lock();
        try {
            return map.get(key);
        } catch (final Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        lock.lock();
        try {
            map.put(key, value);
        } catch (final Exception e) {
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getName() {
        return "mutexlock";
    }
}

