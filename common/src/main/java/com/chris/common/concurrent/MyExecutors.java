package com.chris.common.concurrent;

/**
 * User: zhong.huang
 * Date: 12-8-22
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutors {
    public static void main(String[] args) {
        //cachedThreadPool();
        fixedThreadPool();
    }

    public static void cachedThreadPool() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }

    public static void fixedThreadPool() {
        //三个线程来执行五个任务
        ExecutorService exec = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }

    public static void SingleThreadExecutor() {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 2; i++) {
            exec.execute(new LiftOff());
        }
    }
}
/*
cachedThreadPool
上面的程序中，有10个任务，采用CachedThreadPool模式，exec没遇到一个LiftOff的对象(Task)，
就会创建一个线程来处理任务。现在假设遇到到第4个任务时，之前用于处理第一个任务的线程已经执行完成任务了，
那么不会创建新的线程来处理任务，而是使用之前处理第一个任务的线程来处理这第4个任务。
接着如果遇到第5个任务时，前面那些任务都还没有执行完，那么就会又新创建线程来执行第5个任务。
否则，使用之前执行完任务的线程来处理新的任务。

fixedThreadPool
FixedThreadPool模式会使用一个优先固定数目的线程来处理若干数目的任务。
规定数目的线程处理所有任务，一旦有线程处理完了任务就会被用来处理新的任务(如果有的话)。
这种模式与上面的CachedThreadPool是不同的，
CachedThreadPool模式下处理一定数量的任务的线程数目是不确定的。
而FixedThreadPool模式下最多 的线程数目是一定的。

SingleThreadExecutor模式只会创建一个线程。它和FixedThreadPool比较类似，不过线程数是一个。
如果多个任务被提交给SingleThreadExecutor的话，那么这些任务会被保存在一个队列中，并且会按照任务提交的顺序，
一个先执行完成再执行另外一个线程。
SingleThreadExecutor模式可以保证只有一个任务会被执行。这种特点可以被用来处理共享资源的问题而不需要考虑同步的问题。
 */
