package com.chris.common.algo;

public class Singleton {

    private static Singleton singleton = null;

    private Singleton() {
    }

    /* 安全的方式 1 */
    public synchronized static Singleton getInstance3() {
        if (null == singleton) {
            singleton = new Singleton();
            System.out.print(" new ");
        }
        return singleton;
    }

    public static Singleton getInstance() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                    System.out.print(" new ");
                }
            }
        }
        return singleton;
    }

    public synchronized static Singleton getInstance2() {
        if (null == singleton) {
            singleton = new Singleton();
            System.out.print(" new ");
        }
        return singleton;
    }

    public synchronized static Singleton getInstance4() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                singleton = new Singleton();
                System.out.print(" new ");
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        Singleton.getInstance();
        Singleton.getInstance();
    }
}

/*
 安全的方式 3 
public class ResourceFactory {   
    private static class ResourceHolder {   
        public static Resource resource = new Resource();   
    }   
  
    public static Resource getResource() {   
        return ResourceFactory.ResourceHolder.resource;   
    }   
  
    static class Resource {   
    }   
}  
*/
