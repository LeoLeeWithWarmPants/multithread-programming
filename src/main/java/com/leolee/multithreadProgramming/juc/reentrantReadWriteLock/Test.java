package com.leolee.multithreadProgramming.juc.reentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName Test
 * @Description: reentrantReadWriteLock，读写锁，可以提高读取操作的性能，让读并发，写加锁
 * @Author LeoLee
 * @Date 2021/3/7
 * @Version V1.0
 **/
@Slf4j
public class Test {

    public static void main(String[] args) {
        Test.test2();
        System.out.println((1 << 16) - 1);
    }

    /*
     * 功能描述: <br>
     * 〈验证读写锁——多线程【读】操作是否互斥〉
     * 结果：读写锁的读操作是不互斥的
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2021/3/7 13:55
     */
    public static void test1() {
        ReadWriteLock readWriteLock = new ReadWriteLock();
        new Thread(() -> {
            readWriteLock.read();
        }, "t1").start();
        new Thread(() -> {
            readWriteLock.read();
        }, "t2").start();
    }

    /*
     * 功能描述: <br>
     * 〈验证读写锁——多线程【读写】操作是否互斥〉
     * 结果：读写锁的读写操作是互斥的
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2021/3/7 13:55
     */
    public static void test2() {
        ReadWriteLock readWriteLock = new ReadWriteLock();
        new Thread(() -> {
            readWriteLock.read();
        }, "t1").start();
        new Thread(() -> {
            readWriteLock.write();
        }, "t2").start();
    }

}

@Slf4j
class ReadWriteLock {
    private Object data;

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    //读锁
    private ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
    //写锁
    private ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock();


    public Object read() {
        log.info("获取读锁...");
        readLock.lock();
        try {
            log.info("读取操作");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            log.info("释放读锁...");
            readLock.unlock();
        }
        return data;
    }

    public void write() {
        log.info("获取写锁...");
        writeLock.lock();
        try {
            log.info("写入操作");
        } finally {
            log.info("释放写锁...");
            writeLock.unlock();
        }
    }
}
