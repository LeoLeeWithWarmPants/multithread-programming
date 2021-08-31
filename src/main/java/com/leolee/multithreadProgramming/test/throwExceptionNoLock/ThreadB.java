package com.leolee.multithreadProgramming.test.throwExceptionNoLock;

/**
 * @ClassName ThreadB
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class ThreadB extends Thread {

    private Service serive;

    public ThreadB(Service service) {
        super();
        this.serive = service;
    }

    @Override
    public void run() {
        serive.testMethod();
    }
}
