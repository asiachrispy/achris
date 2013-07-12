package com.chris.common.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WriteLockMapWrapper implements MapWrapper{
	  private final Map<Object,Object> map;
	 
	  private final Lock lock;
	 
	 
	  public WriteLockMapWrapper() {
	    map = new HashMap<Object,Object>();
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
	    return map.get(key);
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
	    return "writelock";
	  }
	}

