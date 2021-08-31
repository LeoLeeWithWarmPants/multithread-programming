package com.leolee.multithreadProgramming.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Test
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/11/29
 * @Version V1.0
 **/
@Slf4j
public class RunnbalTest {

    public static class TestThread extends Thread {

        @Override
        public void run() {
            log.info("thread:" + Thread.currentThread().getName());
        }
    }

    /*
    * run是同步，在当前主线程执行，start才是异步
    */
    public static void main(String[] args) {

        //========================Thread==============================

        TestThread testThread = new TestThread();
        testThread.run();

        Thread testThread2 = new Thread(){
            @Override
            public void run() {
                log.info("thread:" + Thread.currentThread().getName());
            }
        };
        testThread2.start();

        //========================Runnable==============================

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                log.info("thread:" + Thread.currentThread().getName());
            }
        };
        Thread testRunnable = new Thread(runnable);
        testRunnable.start();

    }
}
