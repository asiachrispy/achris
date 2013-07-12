package com.chris.common.concurrent;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 下午5:35
 */
public class LiftOff implements Runnable {
    protected int countDown = 3; //Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ") ";
    }

    public void run() {
        while (countDown-- > 0) {
            System.out.print(status());
            Thread.yield();
        }
    }
}
