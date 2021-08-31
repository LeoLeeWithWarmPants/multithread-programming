package com.leolee.multithreadProgramming.test.objectVariate;

/**
 * @ClassName MyObject
 * @Description: synchronized方法与锁对象
 * @Author LeoLee
 * @Date 2020/8/24
 * @Version V1.0
 **/
public class MyObject {

    public synchronized void methodA() {

        try {
            System.out.println("begin methodA threadName = " + Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public synchronized void methodB() {

        try {
            System.out.println("begin methodB threadName = " + Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
