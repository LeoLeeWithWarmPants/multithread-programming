package com.leolee.multithreadProgramming.test.synLockIn1;

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
        Service service = new Service();
        service.service1();
    }
}
