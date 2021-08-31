package com.leolee.multithreadProgramming.test.synLockIn2;

/**
 * @ClassName Sub
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Sub extends Main {

    public synchronized void operateISubMethod() {

        try {
            while (i > 0) {
                i--;
                System.out.println("sub print i=" + i);
                Thread.sleep(100);
                this.operateIMainMethod();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
