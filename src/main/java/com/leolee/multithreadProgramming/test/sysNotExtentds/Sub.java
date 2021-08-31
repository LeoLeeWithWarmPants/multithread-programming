package com.leolee.multithreadProgramming.test.sysNotExtentds;

/**
 * @ClassName Sub
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Sub extends Main {

    @Override
    public void serviceMethod() {

        try {
            System.out.println("sub sleep begin threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("sub sleep end threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            super.serviceMethod();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
