package com.leolee.multithreadProgramming.test.sysNotExtentds;


/**
 * @ClassName ThreadA
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class ThreadA extends Thread {

    private Sub sub;

    public ThreadA(Sub sub) {
        super();
        this.sub = sub;
    }

    @Override
    public void run() {
        sub.serviceMethod();
    }
}
