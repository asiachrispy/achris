package com.chris.common.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 13-7-12
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class CountDownLatchDemo {

    private static final int PLAYER_AMOUNT = 5;

    public CountDownLatchDemo() {
    }

    public static void main(String[] args) {

        // 对于每位运动员，CountDownLatch减1后即结束比赛
        CountDownLatch begin = new CountDownLatch(1);

        // 对于整个比赛，所有运动员结束后才算结束
        CountDownLatch end = new CountDownLatch(PLAYER_AMOUNT);

        Player[] plays = new Player[PLAYER_AMOUNT]; //定义五个运行员进行测试

        for (int i = 0; i < PLAYER_AMOUNT; i++){
            plays[i] = new Player(i + 1, begin, end);
        }

        // 设置特定的线程池，大小为5
        ExecutorService exe = Executors.newFixedThreadPool(PLAYER_AMOUNT);   //线程调度池，分配固定数目的线程
        for (Player p : plays) {
            exe.execute(p); // 分配线程
        }

        System.out.println("Race begins!");
        begin.countDown();   //此语句代表，当上面五个线程同时就序后，五个线程才同时执行，与下面的begin.await();对应
        try {
            end.await(); // 等待end状态变为0，即为比赛结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Race ends!");
        }
        exe.shutdown();

    }

}

class Player implements Runnable {

    private int id;

    private CountDownLatch begin;

    private CountDownLatch end;

    public Player(int i, CountDownLatch begin, CountDownLatch end) {

        super();

        this.id = i;

        this.begin = begin;

        this.end = end;

    }


    public void run() {

        try {

            begin.await(); // 等待begin的状态为0

            Thread.sleep((long) (Math.random() * 100)); // 随机分配时间，即运动员完成时间

            System.out.println("Play" + id + " arrived.");

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            end.countDown(); // 使end状态减1，最终减至0

        }

    }

}