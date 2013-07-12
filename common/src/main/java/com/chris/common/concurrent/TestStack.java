package com.chris.common.concurrent;

import java.util.LinkedList;

public class TestStack extends Thread {

    LinkedList l = new LinkedList();

    public synchronized void push(Object o) {
        synchronized (l) {
            System.out.println("push object : " + o.toString());

            l.addLast(o);
            notify();
        }
    }

    public synchronized Object pop() {
        synchronized (l) {
            Object o = null;
            if (l.size() < 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            o = l.removeLast();
            System.out.println("pop object : " + o.toString());
            return o;
        }
    }

    public static void main(String[] args) {
        TestStack s = new TestStack();
        TestStack ss = new TestStack();

        Thread t1 = new Thread(s);
        Thread t2 = new Thread(ss);

        t1.run();
        t2.run();

        s.push("a1");
        s.push("a2");
        s.push("a3");

        s.pop();

        ss.push("b1");
        ss.push("b2");
        ss.push("b3");

        ss.pop();
        ss.pop();
        ss.pop();

        s.pop();
        s.pop();
        s.pop();

    }

}
