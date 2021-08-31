package com.leolee.multithreadProgramming.threadPool.customThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPool
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/2/24
 * @Version V1.0
 **/
@Slf4j
public class ThreadPool {

    //线程数
    private int coreThreadSize;

    //获取任务的超时时间
    private long timeout;
    private TimeUnit timeUnit;

    //任务队列
    public BlockingQueue<Runnable> taskQueue;

    //线程集合
    private HashSet<Worker> workThreads = new HashSet<>();

    private RejectPolicy<Runnable> rejectPolicy;

    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //1.当task不为空，执行任务
            //2.当task执行完毕，从任务队列获取任务并执行
//            while (task != null || (task = taskQueue.take()) != null) {
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    log.info("正在执行task{}......", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }

            synchronized (workThreads) {
                log.info("worker{}被移除", this);
                workThreads.remove(this);
            }
        }
    }

    public ThreadPool(int coreThreadSize, long timeout, TimeUnit timeUnit, int queueCapcity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreThreadSize = coreThreadSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapcity);
        this.rejectPolicy = rejectPolicy;
    }

    //执行任务方法
    public void execute(Runnable task) {

        //当任务数没有超过coreThreadSize时，直接交给Worker对象执行
        //超过coreThreadSize时，任务放入队列等待
        synchronized (workThreads) {
            if (workThreads.size() < coreThreadSize) {
                Worker worker = new Worker(task);
                log.info("新增worker{}, task{}", worker, task);
                workThreads.add(worker);
                worker.start();
            } else {
                log.info("没有空闲worker，任务将加入队列{}", task);
//                taskQueue.put(task);//死等
                /*当队列满了之后会有如下情况
                1.死等
                2.带超时的等待
                3.让调用者放弃
                4.让调用者抛出异常
                5.让调用者自己执行任务
                */

                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }
}
