package com.leolee.multithreadProgramming.test.objectVariate;

/**
 * @ClassName ThreadA
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/24
 * @Version V1.0
 **/
public class ThreadA extends Thread {

    private MyObject object;

    public ThreadA(MyObject object) {
        super();
        this.object = object;
    }

    @Override
    public void run() {
        object.methodA();
    }
}
