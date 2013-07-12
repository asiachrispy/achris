package com.chris.common.design.obs;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MailObserver  implements Observer {

    /**
     * 用来发送邮件的观察者
     */
    public void update(Observable o, Object arg) {
        System.out.println("发送邮件的观察者已经被执行");
        try {

            Random random = new Random();
            int  time = Math.abs(random.nextInt()) %100;
            System.out.println("MailObserver等待 " + time );
            Thread.currentThread().join(time);

        } catch (Exception e) {

        }
    }
}

