package com.leolee.multithreadProgramming.test.throwExceptionNoLock;

/**
 * @ClassName Service
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Service {

    public synchronized void testMethod() {
        if (Thread.currentThread().getName().equals("a")) {
            System.out.println("ThreadName=" + Thread.currentThread().getName() + " beginTime=" + System.currentTimeMillis());
            while (true) {
                if (String.valueOf(Math.random()).substring(0, 8).equals("0.123456")) {//模拟业务处理的时间不确定性
                    System.out.println("ThreadName=" + Thread.currentThread().getName() + " exceptionTime=" + System.currentTimeMillis());
                    Integer.valueOf("a");
                }
            }
        } else {
            System.out.println("Thread B run time=" + System.currentTimeMillis());
        }
    }
}
