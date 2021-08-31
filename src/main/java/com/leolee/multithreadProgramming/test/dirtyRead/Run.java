package com.leolee.multithreadProgramming.test.dirtyRead;

/**
 * @ClassName Run
 * @Description:
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Run {

    public static void main(String[] args) {

        try {
            PublicVar publicVarRef = new PublicVar("A", "AA");
            ThreadA threadA = new ThreadA(publicVarRef);
            threadA.start();
            Thread.sleep(2000);
            publicVarRef.getValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
