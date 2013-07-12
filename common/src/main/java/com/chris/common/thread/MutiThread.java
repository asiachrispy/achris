package com.chris.common.thread;

/**
 * User: zhong.huang
 * Date: 12-8-30
 * Time: 上午11:28
 */

/**
 * 测试扩展Thread类实现的多线程程序
 */
public class MutiThread extends Thread {

    public void run() {
        synchronized (this) {
            if (n <= 10) {
                System.out.println(this.getName() + "n :" + n);
            }
            n++;
            this.interrupt();
            this.notifyAll();
        }
    }

    private static int n = 1;

    public static void main(String[] args) {
        MutiThread[] ts = new MutiThread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new MutiThread();
            ts[i].start();
        }

    	for (MutiThread t: ts) {
    		t.isAlive();
    	}

        System.out.println("end");
    }
}
