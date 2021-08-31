package com.leolee.multithreadProgramming.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadStatusTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/11/29
 * @Version V1.0
 **/
@Slf4j
public class ThreadStatusTest {

    //============================sleep test=====================================

    /*
     * 功能描述: <br>
     * 〈测试线程sleep之后的状态变化〉
     * @Param:
     * @Return:
     * @Author: LeoLee
     * @Date: 2020/11/29 17:44
     */
    public void testSleepStatus() throws InterruptedException {
        Thread thread1 = new Thread((Runnable) () -> {
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        log.info(thread1.getName() + ":" + thread1.getState().toString());

        Thread.sleep(1000);
        log.info(thread1.getName() + ":" + thread1.getState().toString());
    }


    /*
     * 功能描述: <br>
     * 〈Thread Interrupte打断测试〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/29 18:33
     */
    public void testInterrupte() throws InterruptedException {

        log.info("证明睡眠中的线程被打断会抛出InterruptedException");

        Thread t1 = new Thread((Runnable) () -> {
            try {
                log.info("currentThread[{}] will sleep immediately", Thread.currentThread().getName());
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                log.info("sleeping thread [{}] is waked up by other thread's interrupted option", Thread.currentThread().getName());
                e.printStackTrace();
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);

        log.info("[{}] will Interrupte [{}]", Thread.currentThread().getName(), t1.getName());

        t1.interrupt();
    }

    /*
     * 功能描述: <br>
     * 〈睡眠结束后并不是可以立刻执行的〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/29 18:44
     */
    public void testSleepOverIsnotToExecute() {

        log.info("证明睡眠结束后并不是可以立刻执行的");
        log.info("因为cpu的时间片当前可能还没有回到该线程上");
    }


    //============================yeild test=====================================


    //============================线程优先级=====================================

    /*
     * 功能描述: <br>
     * 〈测试线程优先级的不稳定性〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/29 19:02
     */
    public void testPriority() {

        Thread t1 = new Thread(() -> {
            int count = 0;
            for (; ; ) {
                System.out.printf("%s count:%d\n", Thread.currentThread().getName(), count++);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            int count = 0;
            for (; ; ) {
                //Thread.yield();//理论上让出使用权之后，当前线程的将执行的少一些
                System.out.printf("%s count:%d\n", Thread.currentThread().getName(), count++);
            }
        }, "t2");

        //设置优先级
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
    }

    //============================join test=====================================

    /*
     * 功能描述: <br>
     * 〈为什么要使用join
     * join可以使当前线程线程等待（阻塞）调用join方法的线程执行之后再执行
     * 并且当前线程可以调用多个其他线程的join方法达到线程间同步的目的〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/29 21:30
     */
    int r = 0;
    public void testJoin() throws InterruptedException {

        log.info("{}开始", Thread.currentThread().getName());

        Thread t1 = new Thread(() -> {
            log.info("{}开始", Thread.currentThread().getName());
            try {
                //sleep1秒
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}结束", Thread.currentThread().getName());
            r = 10;
        }, "t1");

        t1.start();
        t1.join();//直到t1执行结束之后，主线程才会继续执行
        log.info("r={}", r);
        log.info("{}结束", Thread.currentThread().getName());
    }


    //============================interrupt test=====================================

    /*
     * 功能描述: <br>
     * 〈 打断 sleep wait join〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/30 10:38
     */
    public void interruptSleep() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                log.info("{} to do somthing", Thread.currentThread().getName());
                Thread.sleep(5*1000);
                log.info("{} sleep is over", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        log.info("{} interrupt {}", Thread.currentThread().getName(), t1.getName());
        t1.interrupt();
        log.info("interrupt在打断sleep&wait&join的时候，会把该线程的isInterrupt改成false");
        log.info("{} status:{}", t1.getName(), t1.getState());
        log.info("{} isInterrupt:{}", t1.getName(), t1.isInterrupted());
    }


    /*
     * 功能描述: <br>
     * 〈打断正常运行的线程〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/30 10:38
     */
    public void interrupt() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("{} 被打断", Thread.currentThread().getName());
                    break;
                }
            }
        }, "t1");

        t1.start();

        Thread.sleep(1000);

        log.info("{} interrupt {}", Thread.currentThread().getName(), t1.getName());
        t1.interrupt();
        log.info("{} status:{}", t1.getName(), t1.getState());
        log.info("{} isInterrupt:{}", t1.getName(), t1.isInterrupted());
    }

    //============================interrupt test(two phase ter)=====================================

    @Slf4j
    static
    class TwoPhaseTermination {

        private Thread monitor;

        //启动监控线程
        public void start() {
            monitor = new Thread(() -> {
                //模拟线程在运行当中
                for (;;) {
                    Thread monitor = Thread.currentThread();
                    if (monitor.isInterrupted()) {
                        log.info("{}被打断，执行被打断的之后的处理......", monitor.getName());
                        break;
                    } else {
                        try {
                            Thread.sleep(1000);
                            log.info("{}运行正常，执行业务处理......", monitor.getName());
                        } catch (InterruptedException e) {
                            //如果在sleep的时候被打断了，要重置线程的打断状态为true，如果没有这一步，会打断失败，因为sleep wait join会将打断状态设置为false
                            monitor.interrupt();
                            e.printStackTrace();
                        }
                    }
                }
            }, "monitor");

            monitor.start();
        }

        //停止监控线程
        public void stop() {
            monitor.interrupt();
        }

    }


    public static void main(String[] args) throws InterruptedException {

        ThreadStatusTest test = new ThreadStatusTest();

        log.info("==============================sleep test====================================");

        //测试线程sleep之后的状态变化
        //test.testSleepStatus();

        //Thread Interrupte打断测试
        //test.testInterrupte();

        //睡眠结束后并不是可以立刻执行的
        //test.testSleepOverIsnotToExecute();

        log.info("==============================yield test====================================");

        /*当前线程的在执行的过程中拥有cpu的使用权，但是调用了yield方法之后，会让出cpu的使用权，当前线程的状态编程Runnbale
        * 但是yield的实现是依赖操作系统的任务调度器，可能当前只有这一个线程在执行，所以任务调度器可能还是让当前线程拥有cpu使用权
        * */

        log.info("==============================线程优先级====================================");

        /*线程优先级是怼任务调度器的一个提示，调度器可以遵循线程优先级也可以不遵循
        * 当cpu比较繁忙的时候，那么线程优先级较高的线程会获得更多的时间片（也就是更多次的cpu使用权），在cpu比较空闲的时候线程优先级几乎没有什么作用
        * */

        //测试 yield 和 线程优先级的不稳定性
        //test.testPriority();

        log.info("==============================join test====================================");
        /*join可以使当前线程线程等待（阻塞）调用join方法的线程执行之后再执行*/
        //test.testJoin();

        log.info("==============================interrupt test====================================");
        /*interrupt作用：
        *       1.打断处于阻塞状态的线程，sleep、wait、join
        *       2.打断正在执行的线程
        * */
        //test.interruptSleep();
        //断正常运行的线程
        //test.interrupt();
        //两阶段终止模式
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        Thread.sleep(3*1000);
        twoPhaseTermination.stop();
    }
}
