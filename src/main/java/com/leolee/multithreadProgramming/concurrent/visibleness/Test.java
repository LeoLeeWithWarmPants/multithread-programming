package com.leolee.multithreadProgramming.concurrent.visibleness;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Test
 * @Description: 可见性问题
 * @Author LeoLee
 * @Date 2020/12/20
 * @Version V1.0
 **/
@Slf4j
public class Test {

    static boolean run = true;

    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        //可见性问题现象
//        Thread thread = new Thread(() -> {
//            while (run) {
//                //...
//            }
//        });
//        thread.start();
//
//        Thread.sleep(1000);
//        log.info("停止线程循环");
//        run = false;

        //给run加volatile关键字，防止run被从主存中拷贝到线程的私有内存区

        //synchronized防止run被从主存中拷贝到线程的私有内存区
        Thread thread = new Thread(() -> {
            while (true) {
                synchronized (obj) {
                    if (!run) {
                        break;
                    }
                }
            }
        });
        thread.start();

        Thread.sleep(1000);
        log.info("停止线程循环");
        synchronized (obj) {
            run = false;
        }
    }
}
