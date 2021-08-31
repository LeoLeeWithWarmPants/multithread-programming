package com.leolee.multithreadProgramming.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName CountDownLatchTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/2
 * @Version V1.0
 **/
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchTest.test1();
    }

    public static void test1() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println("所有人离开教室，" + Thread.currentThread().getName() + "开始锁门");
    }

}
