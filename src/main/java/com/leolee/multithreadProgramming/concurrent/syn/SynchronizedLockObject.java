package com.leolee.multithreadProgramming.concurrent.syn;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SynchronizedEightLock
 * @Description: 线程八锁（就是关于锁对象的一个题目而已，这些题就是用来判断锁对象是谁）
 * @Author LeoLee
 * @Date 2020/11/30
 * @Version V1.0
 **/
@Slf4j
public class SynchronizedLockObject {

    //==============================锁 this==============================

    public synchronized void a() {
        log.info("1");
    }

    public synchronized void b() {
        log.info("2");
    }

    //==============================锁 类的class==============================

    public static synchronized void c() {
        log.info("1");
    }

    public static synchronized void d() {
        log.info("2");
    }


    public static void main(String[] args) {

        SynchronizedLockObject s = new SynchronizedLockObject();

        log.info("==============================锁 this==============================");

        /*相当于synchronized代码块锁this,锁的是当前类的【对象】*/
        new Thread(() -> {
            log.info("{} begin", Thread.currentThread().getName());
            s.a();
        }, "a").start();
        new Thread(() -> {
            log.info("{} begin", Thread.currentThread().getName());
            s.b();
        }, "b").start();


        log.info("==============================锁 类的class==============================");
        /*相当于synchronized代码块锁SynchronizedEightLock.class*/
        new Thread(() -> {
            log.info("{} begin", Thread.currentThread().getName());
            s.c();
        }, "c").start();
        new Thread(() -> {
            log.info("{} begin", Thread.currentThread().getName());
            s.d();
        }, "d").start();
    }
}
