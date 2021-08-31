package com.leolee.multithreadProgramming.test.throwExceptionNoLock;

/**
 * @ClassName ThreadA
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class ThreadA extends Thread {

    private Service serive;

    public ThreadA(Service service) {
        super();
        this.serive = service;
    }

    @Override
    public void run() {
        serive.testMethod();
    }
}
