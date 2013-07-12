package com.chris.common.interview;

import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

/**
 * User: chris
 * Date: 12-9-11
 * Time: 下午6:26
 */
public class MTest {

    private static MemcachedClient mc =  null;
    static  {
        try{
           mc = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        MTest mTest = new MTest();
        String key1 = "asia:chris:test:key1-set";
        String key2 = "asia:chris:test:key2-add";
        System.out.println(
        mTest.set(key1, "hello,chris!") + "\n" +
        mTest.add(key2, "greate day!") +  "\n" +
        mTest.append(key2, "key2-append") +  "\n" +
        mTest.prepend(key2, "key2-prepend") + "\n" +
        mTest.get(key1) + "\n" +
        mTest.get(key2) + "\n" +
        mTest.delete(key1) + "\n" +
        mTest.get(key1));

        mc.shutdown();
    }
    
    public boolean set(String key, String value) {
        try {
            Future<Boolean> b = mc.set(key, 900, value);
            return b.get().booleanValue();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public String get(String key) {
        return (String)mc.get(key);
    }

    public boolean add(String key, String value) {
        try {
            Future<Boolean> b = mc.add(key, 900, value);
            return b.get().booleanValue();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public boolean append(String key, String value) {
        try {
            Future<Boolean> b = mc.append(0,key, value);
            return b.get().booleanValue();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public boolean prepend(String key, String value) {
        try {
            Future<Boolean> b =  mc.prepend(0,key, value);
            return b.get().booleanValue();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            return false;
        }
    }

    public boolean delete(String key) {
        try {
            Future<Boolean> b = mc.delete(key);
            return b.get().booleanValue();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            return false;
        }
    }
}
/*
cache数据写入操作方法
set方法
将数据保存到cache服务器，如果保存成功则返回true
如果cache服务器存在同样的key，则替换之
set有5个重载方法，key和value是必须的参数，还有过期时间，hash码，value是否字符串三个可选参数

add方法
将数据添加到cache服务器,如果保存成功则返回true
如果cache服务器存在同样key，则返回false
add有4个重载方法，key和value是必须的参数，还有过期时间，hash码两个可选参数

replace方法
将数据替换cache服务器中相同的key,如果保存成功则返回true
如果cache服务器不存在同样key，则返回false
replace有4个重载方法，key和value是必须的参数，还有过期时间，hash码两个可选参数
建议分析key的规律，如果呈现某种规律有序，则自己构造hash码，提高存储效率

cache数据读取操作方法
使用get方法从cache服务器获取一个数据
如果写入时是压缩的或序列化的，则get的返回会自动解压缩及反序列化
get方法有3个重载方法，key是必须的参数，hash码和value是否字符串是可选参数

使用getMulti方法从cache服务器获取一组数据
get方法的数组实现，输入参数keys是一个key数组
返回是一个map

通过cache使用计数器
使用storeCounter方法初始化一个计数器
使用incr方法对计数器增量操作
使用decr对计数器减量操作
 */
