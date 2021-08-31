package com.leolee.multithreadProgramming.test.synLockIn2;

/**
 * @ClassName MyThread
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class MyThread extends Thread {

    @Override
    public void run() {
        Sub sub = new Sub();
        sub.operateISubMethod();
    }
}
