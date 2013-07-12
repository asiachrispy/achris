package com.chris.common.concurrent;

/**
 * User: zhong.huang
 * Date: 11-12-1
 */
class Consumer extends Thread // 消费者线程
{
    private ShareData s;

    Consumer(ShareData s) {
        this.s = s;
    }

    public void run() {
        char ch;
        do {
            try {
                Thread.sleep((int) (Math.random() * 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ch = s.getShareChar(); // 从仓库中取出产品
            System.out.println(ch + " is consumed by Consumer. ");
        } while (ch != 'D');

    }
}
