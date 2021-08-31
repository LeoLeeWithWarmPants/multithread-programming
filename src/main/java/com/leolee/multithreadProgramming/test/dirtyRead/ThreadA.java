package com.leolee.multithreadProgramming.test.dirtyRead;

/**
 * @ClassName ThreadA
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class ThreadA extends Thread {

    private PublicVar publicVar;

    public ThreadA(PublicVar publicVar) {
        super();
        this.publicVar = publicVar;
    }

    @Override
    public void run() {
        super.run();
        publicVar.setValue("B", "BB");
    }
}
