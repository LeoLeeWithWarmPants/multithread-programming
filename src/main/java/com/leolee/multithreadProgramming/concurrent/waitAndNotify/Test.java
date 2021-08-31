package com.leolee.multithreadProgramming.concurrent.waitAndNotify;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Test
 * @Description: wait 和 notify测试
 * @Author LeoLee
 * @Date 2020/12/2
 * @Version V1.0
 **/
@Slf4j
public class Test {

    public final static Object obj = new Object();

    /*
     * 功能描述: <br>
     * 〈wait&notify用法测试〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/2 15:22
     */
    public void normalTest() throws InterruptedException {

        new Thread(() -> {
            synchronized (obj) {
                log.info("{}开始执行...", Thread.currentThread().getName());
                try {
                    //让当前线程t1在obj上一直等待
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{}的其他代码...", Thread.currentThread().getName());
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.info("{}开始执行...", Thread.currentThread().getName());
                try {
                    //让当前线程t1在obj上一直等待
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{}的其他代码...", Thread.currentThread().getName());
            }
        }, "t2").start();

        new Thread(() -> {
            synchronized (obj) {
                log.info("{}开始执行...", Thread.currentThread().getName());
                try {
                    //让当前线程t1在obj上一直等待
                    obj.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{}的其他代码...", Thread.currentThread().getName());
            }
        }, "t3").start();

        //此时t1 t2线程都进入了obj的waitset集合，处于等待状态

        Thread.sleep(2*1000);

        //主线程获得锁，唤起在obj上等待的线程
        synchronized (obj) {
            obj.notify();//唤醒obj的一个wait线程（随机挑一个唤醒）

            Thread.sleep(2*1000);

            obj.notifyAll();//唤醒所有obj上等待的线程
        }
    }


    public static boolean isCigarette = false;
    public static boolean isFood = false;

    /*
     * 功能描述: <br>
     * 〈wait notify的正确用法〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/2 16:10
     */
    public void rightWay() throws InterruptedException {

        new Thread(() -> {
            synchronized (obj) {
                log.info("{}开始写代码...", Thread.currentThread().getName());
                while (!isCigarette) {
                    try {
                        log.info("{}没烟写不下去休息去了...", Thread.currentThread().getName());
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (isCigarette) {
                    log.info("{}抽完烟继续写代码...", Thread.currentThread().getName());
                } else {
                    log.info("{}被叫醒之后发现还是没烟心态崩了，回家了...", Thread.currentThread().getName());
                }

            }
        }, "张三").start();

        new Thread(() -> {
            synchronized (obj) {
                log.info("{}开始写代码...", Thread.currentThread().getName());
                while (!isFood) {
                    try {
                        log.info("{}没外卖写不下去休息去了...", Thread.currentThread().getName());
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (isFood) {
                    log.info("{}吃上外卖继续写代码...", Thread.currentThread().getName());
                } else {
                    log.info("{}被叫醒之后发现还是没外卖心态崩了，回家了...", Thread.currentThread().getName());
                }

            }
        }, "李四").start();

        Thread.sleep(2500);

        new Thread(() -> {
            synchronized (obj) {
                log.info("{}来送外卖了...", Thread.currentThread().getName());
                isFood = true;
                obj.notifyAll();
            }
        }, "王五").start();
    }

    public static void main(String[] args) throws InterruptedException {

        Test test = new Test();
        //test.normalTest();
        test.rightWay();
    }
}
