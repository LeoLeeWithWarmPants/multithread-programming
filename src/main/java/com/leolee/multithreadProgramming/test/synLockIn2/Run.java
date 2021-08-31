package com.leolee.multithreadProgramming.test.synLockIn2;

/**
 * @ClassName Run
 * @Description: 重入锁支持在子父类继承的情况下
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Run {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
