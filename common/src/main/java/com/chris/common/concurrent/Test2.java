package com.chris.common.concurrent;

/*
* 这次“读”线程接收到了lock.lockInterruptibly()中断，并且有效处理了这个“异常”。
* 好奇的读者，肯定要探个究竟，为什么 ReentrantLock能做到这点，接下来，我们去迷宫探险吧……
*/
public class Test2 {
    public static void main(String[] args) {
        BufferInterruptibly buff = new BufferInterruptibly();

        final Writer2 writer = new Writer2(buff);
        final Reader2 reader = new Reader2(buff);

        writer.start();
        reader.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (; ;) {
                    if (System.currentTimeMillis() - start > 5000) {
                        System.out.println("不等了，尝试中断");
                        reader.interrupt();
                        break;
                    }

                }

            }
        }).start();

    }
}
