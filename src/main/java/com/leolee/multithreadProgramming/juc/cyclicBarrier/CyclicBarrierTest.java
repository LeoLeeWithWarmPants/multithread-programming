package com.leolee.multithreadProgramming.juc.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName CyclicBarrierTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/2
 * @Version V1.0
 **/
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, new Runnable() {
            @Override
            public void run() {
                System.out.println("累加到7，最终线程开始执行");
            }
        });

        for (int i = 0; i < 7; i++) {
            int tempInt = i;
            new Thread(() -> {
                System.out.println("累加到" + tempInt);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("----------------");
            }, String.valueOf(i)).start();
        }
    }
}
