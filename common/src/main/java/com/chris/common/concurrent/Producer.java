package com.chris.common.concurrent;

/**
 * User: zhong.huang
 * Date: 11-12-1
 */
class Producer extends Thread // 生产者线程
{
    private ShareData s;

    Producer(ShareData s) {
        this.s = s;
    }

    public void run() {

        for (char ch = 'A'; ch <= 'D'; ch++) {
            try {
                Thread.sleep((int) (Math.random() * 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            s.putShareChar(ch); // 将产品放入仓库
            System.out.println(ch + " is produced by Producer.");
        }
    }
}
