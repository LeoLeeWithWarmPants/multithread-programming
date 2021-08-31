package com.leolee.multithreadProgramming.test.synLockIn1;

/**
 * @ClassName Service
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Service {

    public synchronized void service1() {
        System.out.println("service1");
        service2();
    }

    public synchronized void service2() {
        System.out.println("Service2");
        service3();
    }

    public synchronized void service3() {
        System.out.println("service3");
    }
}
