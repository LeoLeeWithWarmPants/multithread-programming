package com.leolee.multithreadProgramming.test.throwExceptionNoLock;


/**
 * @ClassName Run
 * @Description: 运行中出现异常，锁自动释放
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Run {

    public static void main(String[] args) {

        try {
            Service service = new Service();
            ThreadA threadA = new ThreadA(service);
            threadA.setName("a");
            threadA.start();
            Thread.sleep(500);
            ThreadB threadB = new ThreadB(service);
            threadB.setName("b");
            threadB.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
