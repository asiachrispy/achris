package com.chris.common.thread;

public class ProCon {
    public static void main(String[] args) {

        Queue queue = new Queue();
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
    }

    static class Queue {
        int size = 0;

        public synchronized void addProduct() {
            if (size == 5) {// 如果队列已满，则调用wait等待消费者取走产品
                try {
                    System.out.println("队列已满，调用wait等待消费者取走产品!");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.size += 1;
            System.out.println("product a pro");
            this.notify();// 生产产品后通知消费者取走产品
        }

        public synchronized void subProduct() {
            if (size == 0) {// 如果队列是空的，则调用wait等待生产者生产产品
                try {
                    System.out.println("队列是空的，则调用wait等待生产者生产产品!");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            size = size - 1;
            System.out.println("consumor a pro");
            this.notify();// 取走产品后通知生产者继续生产产品
        }

    }

    static class Producer implements Runnable {
        Queue queue;

        Producer(Queue queue) {
            this.queue = queue;
        }

        public void run() {// 生产线程

            for (int i = 1; i <= 30; i++) {
                queue.addProduct();
            }
        }
    }

    static class Consumer implements Runnable {
        Queue queue;

        Consumer(Queue queue) {
            this.queue = queue;
        }

        public void run() {// 消费线程
            for (int i = 1; i <= 30; i++) {
                queue.subProduct();
            }
        }
    }
}