package com.chris.common.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: chris
 * Date: 12-8-20
 * Time: 下午7:46
 * Lock接口

 ReentrantLock是Lock的具体类，Lock提供了以下一些方法：
 lock(): 请求锁定，如果锁已被别的线程锁定，调用此方法的线程被阻断进入等待状态。tryLock()：如果锁没被别的线程锁定，进入锁定状态，并返回true。若锁已被锁定，返回false，不进入等待状态。此方法还可带时间参数，如果锁在方法执行时已被锁定，线程将继续等待规定的时间，若还不行才返回false。unlock()：取消锁定，需要注意的是Lock不会自动取消，编程时必须手动解锁。
 代码：

 //生成一个锁
 Lock lock = new ReentrantLock();
 public void accessProtectedResource() {
 lock.lock(); //取得锁定
 try {
     //对共享资源进行操作
     } finally {
     //一定记着把锁取消掉，锁本身是不会自动解锁的
     lock.unlock();
     }
 }

 ReadWriteLock接口
 为了提高效率有些共享资源允许同时进行多个读的操作，但只允许一个写的操作，比如一个文件，只要其内容不变可以让多个线程同时读，不必做排他的锁定，排他的锁定只有在写的时候需要，以保证别的线程不会看到数据不完整的文件。ReadWriteLock可满足这种需要。ReadWriteLock内置两个Lock，一个是读的Lock，一个是写的Lock。多个线程可同时得到读的Lock，但只有一个线程能得到写的Lock，而且写的Lock被锁定后，任何线程都不能得到Lock。ReadWriteLock提供的方法有：
 readLock(): 返回一个读的lockwriteLock(): 返回一个写的lock, 此lock是排他的。
 ReadWriteLock的例子：
 */
public class FileOperator{
    //初始化一个ReadWriteLock
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public String read() {
        //得到readLock并锁定
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            //做读的工作
            return "Read something";
        } finally {
            readLock.unlock();
        }
    }

    public void write(String content) {
        //得到writeLock并锁定
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            //做读的工作
        } finally {
            writeLock.unlock();
        }
    }
}
/*
需要注意的是ReadWriteLock提供了一个高效的锁定机理，但最终程序的运行效率是和程序的设计息息相关的，
比如说如果读的线程和写的线程同时在等待，要考虑是先发放读的lock还是先发放写的lock。如果写发生的频率不高，而且快，
可以考虑先给写的lock。还要考虑的问题是如果一个写正在等待读完成，此时一个新的读进来，是否要给这个新的读发锁，
如果发了，可能导致写的线程等很久。等等此类问题在编程时都要给予充分的考虑。
 */
