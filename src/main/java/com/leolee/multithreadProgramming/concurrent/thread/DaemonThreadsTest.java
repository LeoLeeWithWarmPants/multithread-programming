package com.leolee.multithreadProgramming.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DaemonThreads
 * @Description: 守护线程
 * @Author LeoLee
 * @Date 2020/11/30
 * @Version V1.0
 **/
@Slf4j
public class DaemonThreadsTest {

    //============================守护线程使用方法=====================================

    /*
     * 功能描述: <br>
     * 〈这段代码中，线程t1是一直在运行的，所以java进程也不会结束
     * 当设置t1为守护线程之后，主线程执行结束，t1也就自动结束了〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/30 13:43
     */
    public void testDaemonThread() throws InterruptedException {
        //这个线程将一直运行，即使主线程结束了
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.info("{}结束", Thread.currentThread().getName());
        }, "t1");

        //设置守护线程
        t1.setDaemon(true);

        t1.start();

        Thread.sleep(1000);
        log.info("{}结束", Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {

        DaemonThreadsTest test = new DaemonThreadsTest();
        log.info("=============================守护线程使用方法==================================");
        /*默认情况下，java进程需要等待所有的线程执行结束之后才会结束，有一种特殊的线程，叫做守护线程，只要其他非守护线程执行结束了，守护线程就会结束
        * 常见的守护线程：垃圾回收器；tomcat的Acceptor和poller线程都是守护线程，所以当tomcat shutdown的时候，不会等待他们处理完当前的请求
        * */

        //这段代码中，线程t1是一直在运行的，所以java进程也不会结束,当设置t1为守护线程之后，主线程执行结束，t1也就自动结束了
        test.testDaemonThread();
    }
}
