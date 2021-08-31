package com.leolee.multithreadProgramming.test.sysNotExtentds;


/**
 * @ClassName ThreadB
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class ThreadB extends Thread {

    private Sub sub;

    public ThreadB(Sub sub) {
        super();
        this.sub = sub;
    }

    @Override
    public void run() {
        sub.serviceMethod();
    }
}
