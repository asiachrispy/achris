package com.chris.common.concurrent;

/**
 * User: zhong.huang
 * Date: 11-12-1
 */
class ShareData {

    private char c;

    private boolean isProduced = false; // 信号量

    public synchronized void putShareChar(char c) // 同步方法putShareChar()
    {
        if (isProduced) // 如果产品还未消费，则生产者等待
        {
            try {
                wait(); // 生产者等待

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.c = c;
        isProduced = true; // 标记已经生产
        notify(); // 通知消费者已经生产，可以消费
    }

    public synchronized char getShareChar() // 同步方法getShareChar()
    {
        if (!isProduced) // 如果产品还未生产，则消费者等待
        {
            try {
                wait(); // 消费者等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isProduced = false; // 标记已经消费
        notify(); // 通知需要生产
        return this.c;
    }

}
