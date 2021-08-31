package com.leolee.multithreadProgramming.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @ClassName ABATest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
public class ABATest {

    public static void main(String[] args) {

//        AtomicReference<Integer> integerAtomicReference = new AtomicReference<>(100);
//
//        int expect = integerAtomicReference.get();//100
//
//        //ABA问题
//        new Thread(() -> {
//            integerAtomicReference.compareAndSet(expect, 101);
//            integerAtomicReference.compareAndSet(integerAtomicReference.get(), 100);
//        }, "t1").start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println(integerAtomicReference.compareAndSet(expect, 102) + "\t" + integerAtomicReference.get());
//        }, "t2").start();

        //ABA问题解决
        AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<Integer>(100, 1);//初始值为100，版本号1
        new Thread(() -> {
            int stamp = integerAtomicStampedReference.getStamp();
            System.out.println("t3获取的初始版本号为:" + stamp);
            try {
                Thread.sleep(1000);//保证t4线程可以获取到与此时t3一样的版本号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            integerAtomicStampedReference.compareAndSet(100, 101, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1);//版本号+1
            integerAtomicStampedReference.compareAndSet(101, 100, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1);//版本号+1
        }, "t3").start();

        new Thread(() -> {
            int stamp = integerAtomicStampedReference.getStamp();
            System.out.println("t4获取的初始版本号为:" + stamp);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(integerAtomicStampedReference.compareAndSet(100, 102, stamp, stamp + 1) + "\t" + integerAtomicStampedReference.getReference());
        }, "t4").start();
    }
}
