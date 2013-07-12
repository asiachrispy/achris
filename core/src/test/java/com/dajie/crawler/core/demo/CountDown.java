package com.dajie.crawler.core.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * User: zhong.huang
 * Date: 13-5-17
 */
public class CountDown {

    public static long time(Executor executor, int concurrency, final Runnable action) {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    ready.countDown();
                    try {
                        start.await();
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();
                    }
                }
            });
            try {
                //等待工作者线程准备可以执行，即所有的工作线程均调用ready.countDown()方法。
                ready.await();

                //这里使用nanoTime，是因为其精确度高于System.currentTimeMills()。
                long startNanos = System.nanoTime();
                //该语句执行后，工作者线程中的start.await()均将被唤醒。
                start.countDown();
                //下面的等待，只有在所有的工作者线程均调用done.countDown()之后才会被唤醒。
                done.await();

                return System.nanoTime() - startNanos;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
