package com.chris.common.concurrent.org;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: zhong.huang
 * Date: 13-6-5
 */
public class TestA {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String,String>();
        map.put("a","a");
        map.put("b","b");

        map.get("a");

        map.contains("b");
        map.remove("b");

        ReentrantLock lock = new ReentrantLock();

    }
}
