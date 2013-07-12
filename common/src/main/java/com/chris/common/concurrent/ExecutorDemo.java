package com.chris.common.concurrent;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 下午5:21
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {

        ExecutorService  service = null;

        //产生一个ExecutorService对象，这个对象带有一个大小为poolSize的线程池，若任务数量大于poolSize，任务会被放在一个queue里顺序执行。
        service = Executors.newFixedThreadPool(5);

        //产生一个ScheduledExecutorService对象，这个对象的线程池大小为1，若任务多于一个，任务将按先后顺序执行。
        //service = Executors.newSingleThreadScheduledExecutor();

        //产生一个ExecutorService对象，这个对象只有一个线程可用来执行任务，若任务多于一个，任务将按先后顺序执行。
        //service = Executors.newSingleThreadExecutor();

        //产生一个ExecutorService对象，这个对象带有一个线程池，线程池的大小会根据需要调整，线程执行完任务后返回线程池，供执行下一次任务使用。
        service = Executors.newCachedThreadPool();

        //产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，若任务数量大于poolSize，任务会在一个queue里等待执行
        //service = Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 10; i++) {
            final int count = i;
            service.submit(new Runnable() {
                public void run() {
                    System.out.println(count);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        service.shutdown(); // 最后记得关闭Thread pool
    }
}