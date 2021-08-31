package com.leolee.multithreadProgramming.test.synLockIn1;

/**
 * @ClassName Run
 * @Description: 锁重入
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
