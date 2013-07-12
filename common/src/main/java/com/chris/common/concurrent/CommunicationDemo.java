package com.chris.common.concurrent;

//CommunicationDemo.java 描述：生产者和消费者之间的消息传递过程

public class CommunicationDemo {
    public static void main(String[] args)
    {
        ShareData s = new ShareData();
        new Consumer(s).start();
        new Producer(s).start();

    }

}
