package com.chris.common.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockMapWrapper implements MapWrapper {

    private final Map<Object, Object> map;

    private final Lock readLock;

    private final Lock writeLock;

    public RWLockMapWrapper() {
        map = new HashMap<Object, Object>();
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } catch (final Exception e) {
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public Object get(final Object key) {
        readLock.lock();
        try {
            return map.get(key);
        } catch (final Exception e) {
            // TODO: handle exception
        } finally {
            readLock.unlock();
        }
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        writeLock.lock();
        try {
            map.put(key, value);
        } catch (final Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String getName() {
        return "rwlock";
    }
}

