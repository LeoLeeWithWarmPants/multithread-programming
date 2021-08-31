package com.leolee.multithreadProgramming.juc.lockSupport;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName Test
 * @Description: LockSupport：JDK线程工具类
 * @Author LeoLee
 * @Date 2020/12/7
 * @Version V1.0
 **/
@Slf4j
public class Test {

    public static void normalTest() {

        Thread t1 = new Thread(() -> {
            log.info("{}开始执行", Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("park");
            LockSupport.park();
            log.info("resume");
        }, "t1");
        t1.start();

        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("unpark");
        LockSupport.unpark(t1);
    }

    /*
     * 功能描述: <br>
     * 〈park 和 unpark 的执行顺序不受影响，先调用unpark，线程会在刚park完成后马上唤醒〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/7 10:57
     */
    public static void test() {

        Thread t1 = new Thread(() -> {
            log.info("{}开始执行", Thread.currentThread().getName());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("park");
            LockSupport.park();
            log.info("resume");
        }, "t1");
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("unpark");
        LockSupport.unpark(t1);
    }

    public static void main(String[] args) {
        Test.normalTest();
    }
}
