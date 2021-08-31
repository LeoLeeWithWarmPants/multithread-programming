package com.leolee.multithreadProgramming.test.sysNotExtentds;

/**
 * @ClassName Main
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Main {

    public synchronized void serviceMethod() {

        try {
            System.out.println("main sleep begin threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("main sleep end threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
