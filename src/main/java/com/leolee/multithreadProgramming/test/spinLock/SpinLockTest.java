package com.leolee.multithreadProgramming.test.spinLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName SpinLockTest
 * @Description: 自定义自旋锁
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
public class SpinLockTest {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "unlock");
    }

    public static void main(String[] args) {
        SpinLockTest spinLockTest = new SpinLockTest();

        new Thread(() -> {
            spinLockTest.myLock();
            System.out.println("t1 get lock");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockTest.myUnlock();
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);//保证t1一定是先启动，先持有锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockTest.myLock();

            System.out.println("t2 get lock");

            spinLockTest.myUnlock();
        }, "t2").start();
    }
}
