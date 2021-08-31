package com.leolee.multithreadProgramming.concurrent.syn;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DeadLock
 * @Description: 死锁问题
 * @Author LeoLee
 * @Date 2020/12/7
 * @Version V1.0
 **/
@Slf4j
public class DeadLock {




    /*
     * 功能描述: <br>
     * 〈模拟死锁〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/7 12:24
     */
    public static void testDeadLock() {

        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (a) {
                log.info("lock a");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    log.info("lock b");
                    log.info("do something");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (b) {
                log.info("lock a");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    log.info("lock b");
                    log.info("do something");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {

        DeadLock.testDeadLock();
    }
}
