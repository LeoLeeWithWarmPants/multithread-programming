package com.leolee.multithreadProgramming.test.objectVariate;

/**
 * @ClassName ThreadB
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/24
 * @Version V1.0
 **/
public class ThreadB extends Thread {

    private MyObject object;

    public ThreadB(MyObject object) {
        super();
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        object.methodB();
    }
}
