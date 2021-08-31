package com.leolee.multithreadProgramming.juc.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Test
 * @Description: JUC ReentrantLock测试
 * 相对于 synchronized,ReentrantLock具有如下特点：
 *      1.可中断（一个线程可以取消另一个线程的锁）
 *      2.可以设置超时时间
 *      3.可以设置为公平锁
 *      4.支持多个条件变量（synchronized竞争失败的线程都会进入Monitor的waitList，ReentrantLock可以根据不同的条件变量进入不同的集合）
 *      5.与synchronized一样，都支持锁重入
 *
 *
 * @Author LeoLee
 * @Date 2020/12/7
 * @Version V1.0
 **/
@Slf4j
public class Test {

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    //============================基本使用方法以及可重入测试============================

    public void normalTest() {
        reentrantLock.lock();

        try {
            //临界区，需要被保护的代码块
            log.info("main method 获得锁，开始执行");
            this.m1();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void m1() {
        reentrantLock.lock();

        try {
            //临界区，需要被保护的代码块
            log.info("m1 获得锁，开始执行");
            this.m2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void m2() {
        reentrantLock.lock();

        try {
            //临界区，需要被保护的代码块
            log.info("m2 获得锁，开始执行");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }


    //============================可中断特性测试============================

    public void testInterruptibly() {

        Thread t1 = new Thread(() -> {
            try {
                //如果竞争到锁就继续执行，失败就进入阻塞队列，其他线程可以使用 interrupt 打断，即：你不要继续等待了、
                log.info("{} 尝试获取锁", Thread.currentThread().getName());
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                //被interrupt打断之后，抛出InterruptedException，就代表没有获取到锁,直接return
                e.printStackTrace();
                log.info("{} 未获取到锁，直接return", Thread.currentThread().getName());
                return;
            }

            //获取到锁
            try {
                log.info("{} 获取到锁，并执行代码", Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }
        }, "t1");

        //主线程先获取锁，让t1线程进入阻塞队列
        reentrantLock.lock();
        t1.start();

        //主线程打断t1线程的等待
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{} 打断 {}", Thread.currentThread().getName(), t1.getName());
        t1.interrupt();
    }


    //============================锁超时============================

    public void testLockTimeout() {

        Thread t1 = new Thread(() -> {
            log.info("{} 尝试获得锁", Thread.currentThread().getName());
            //不带参数方法是立刻返回加锁是否成功的结果，带参的是等待一定时间后返回结果，如果在超时时间内获取到锁，则直接返回true
            if (!reentrantLock.tryLock()) {
                log.info("{} 尝试获取锁失败，直接返回", Thread.currentThread().getName());
                return;
            }
            //临界区代码
            try {
                //获得到了锁
                log.info("{} 获取锁成功，开始执行临界区代码", Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }
        }, "t1");

        //主线程获取到锁
        reentrantLock.lock();
        t1.start();
    }

    public void testLockTimeout2() {

        Thread t1 = new Thread(() -> {
            log.info("{} 尝试获得锁", Thread.currentThread().getName());
            //不带参数方法是立刻返回加锁是否成功的结果，带参的是等待一定时间后返回结果，如果在超时时间内获取到锁，则直接返回true
            try {
                if (!reentrantLock.tryLock(2, TimeUnit.SECONDS)) {
                    log.info("{} 尝试获取锁失败，直接返回", Thread.currentThread().getName());
                    return;
                }
            } catch (InterruptedException e) {
                log.info("{} 尝试获取锁被打断，直接返回", Thread.currentThread().getName());
                e.printStackTrace();
                return;
            }
            //临界区代码
            try {
                //获得到了锁
                log.info("{} 获取锁成功，开始执行临界区代码", Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }
        }, "t1");

        //主线程获取到锁
        reentrantLock.lock();
        t1.start();
    }

    //============================多条件变量============================

    public void testCondition() {

        //创建一个新的条件变量（即：一个休息区）
        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();

        reentrantLock.lock();
        //当某些条件不满足进入休息室等待，此处省略条件判断
        try {
            condition1.await();
            condition1.await(1, TimeUnit.SECONDS);//也是可以设置等待超时时间
            condition1.awaitUninterruptibly();//不能被打断的等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //唤醒condition1中某一个等待的线程
        condition1.signal();
        //唤醒condition1中所有等待的线程
        condition1.signalAll();

        //最后要释放锁
    }

    public static void main(String[] args) {

        Test test = new Test();
        test.testCondition();
    }
}
