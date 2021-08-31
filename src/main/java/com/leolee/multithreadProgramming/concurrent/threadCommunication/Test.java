package com.leolee.multithreadProgramming.concurrent.threadCommunication;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Test
 * @Description: 线程间通讯——线程的执行顺序
 * @Author LeoLee
 * @Date 2020/12/14
 * @Version V1.0
 **/
@Slf4j
public class Test {


    //=============================固定顺序执行 wait notify 方法====================================

    /*
     * 功能描述: <br>
     * 〈线程的顺序执行——wait notify方法〉
     * 该实例使t2一定先执行
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/14 21:18
     */
    static final Object object = new Object();
    static boolean t2Runned = false;//表示t2是否运行过

    public void sequenceByWaitNotify() {

        Thread t1 = new Thread(() -> {
            synchronized (object) {
                while (!t2Runned) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("{} 执行", Thread.currentThread().getName());
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (object) {
                log.info("{} 执行", Thread.currentThread().getName());
                t2Runned = true;
                object.notifyAll();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    //=============================固定顺序执行 ReentrantLock法====================================


    /*
     * 功能描述: <br>
     * 〈固定顺序执行 ReentrantLock法〉
     * 该实例使t2一定先执行
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/14 22:28
     */

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public void sequenceByReentrantLock() {

        Condition condition1 = reentrantLock.newCondition();

        Thread t1 = new Thread(() -> {
            reentrantLock.lock();
            try {
                while (!t2Runned) {
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
            log.info("{} 执行", Thread.currentThread().getName());
        }, "t1");

        Thread t2 = new Thread(() -> {
            reentrantLock.lock();
            try {
                log.info("{} 执行", Thread.currentThread().getName());
                t2Runned = true;
                condition1.signalAll();
            } finally {
                reentrantLock.unlock();
            }
        }, "t2");


        t1.start();
        t2.start();
    }


    //=============================固定顺序执行 park unpark 法====================================

    /*
     * 功能描述: <br>
     * 〈固定顺序执行 park unpark 法〉
     * 该实例使t2一定先执行
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/15 0:17
     */
    public void sequenceByPark() {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.info("{} 执行", Thread.currentThread().getName());
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.info("{} 执行", Thread.currentThread().getName());
            LockSupport.unpark(t1);
        }, "t2");


        t1.start();
        t2.start();
    }

    //=============================交替执行wait & notify法====================================

    /*      等待标识    下一个标识
     a      1           2
     b      2           3
     c      3           1
     */

    private int flag;//等待标记
    //循环次数
    private int loopNum;

    private void print(String str, int waitFlag, int nextFlag) {

        for (int i = 0; i < loopNum; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("{}", str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    /*
     * 功能描述: <br>
     * 〈〉
     * @Param: [flag 开始的标记, loopNum循环次数]
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/15 20:47
     */
    public void alternateByWaitNotify(int flag, int loopNum) {

        this.flag = flag;
        this.loopNum = loopNum;

        new Thread(() -> {
            this.print("a", 1, 2);
        }).start();
        new Thread(() -> {
            this.print("b", 2, 3);
        }).start();
        new Thread(() -> {
            this.print("c", 3, 1);
        }).start();
    }


    //=============================交替执行ReentrantLock - await & signal法====================================

    /*
     * 功能描述: <br>
     * 〈交替执行ReentrantLock - await & signal法〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/15 17:44
     */
    public void alternateByAwaitSignal() {

        this.loopNum = 4;

        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        Condition condition3 = reentrantLock.newCondition();

        new Thread(() -> {
            this.print("a", condition1, condition2);
        }).start();

        new Thread(() -> {
            this.print("b", condition2, condition3);
        }).start();

        new Thread(() -> {
            this.print("c", condition3, condition1);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //开始唤起第一个线程
        reentrantLock.lock();
        try {
            condition1.signal();
        } finally {
            reentrantLock.unlock();
        }

    }


    public void print(String str, Condition currentCondition, Condition nextCondition) {

        for (int i = 0; i < loopNum; i++) {
            reentrantLock.lock();
            try {
                currentCondition.await();
                log.info(str);
                nextCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }



    //=============================交替执行LockSupport - park & unpark法====================================
    private Thread t1;
    private Thread t2;
    private Thread t3;
    public void alternateByLockSupport() {

        this.loopNum = 5;

        t1 = new Thread(() -> {
            this.print("a", t2);
        });

        t2 = new Thread(() -> {
            this.print("b", t3);
        });

        t3 = new Thread(() -> {
            this.print("c", t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }

    public void print(String str, Thread nextThread) {
        for (int i = 0; i < loopNum; i++) {
            LockSupport.park();
            log.info(str);
            LockSupport.unpark(nextThread);
        }
    }


    public static void main(String[] args) {

        Test test = new Test();
//        test.sequenceByWaitNotify();
//        test.sequenceByReentrantLock();
//        test.sequenceByPark();
//        test.alternateByWaitNotify(1, 10);
//        test.alternateByAwaitSignal();
        test.alternateByLockSupport();
    }
}
