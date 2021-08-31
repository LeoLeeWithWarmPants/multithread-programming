package com.leolee.multithreadProgramming.test.synLockIn2;

/**
 * @ClassName Main
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Main {

    public int i = 10;

    public synchronized void operateIMainMethod() {

        try {
            i--;
            System.out.println("main print i=" + i);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
