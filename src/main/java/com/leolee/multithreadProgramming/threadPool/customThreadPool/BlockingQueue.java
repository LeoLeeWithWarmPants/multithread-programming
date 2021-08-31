package com.leolee.multithreadProgramming.threadPool.customThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName BlockingQueue
 * @Description: 阻塞队列
 * @Author LeoLee
 * @Date 2021/2/24
 * @Version V1.0
 **/
@Slf4j
public class BlockingQueue<T> {

    //任务队列，Deque是一个双向链表，也可以使用LinkedList来实现Deque，ArrayDeque效率高一些
    private Deque<T> queue = new ArrayDeque<>();

    //队列容量
    private int capcity;

    //锁，保证队列头尾的任务不会被重复执行
    private final ReentrantLock lock = new ReentrantLock();

    //生产者条件变量
    private Condition fullWaitSet = lock.newCondition();

    //消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();

    public BlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    //阻塞队列获取（带超时时间）
    public T poll(long timeout, TimeUnit timeUnit) {

        lock.lock();
        try {
            //转化为纳秒
            long nanos = timeUnit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    //重新赋值的nanos是剩余的等待时间
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return queue.removeFirst();
        } finally {
            lock.unlock();
        }
    }

    //堵塞队列获取（不带超时）
    public T take() {

        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return queue.removeFirst();
        } finally {
            lock.unlock();
        }
    }

    //阻塞队列空闲添加
    public void put(T element) {

        lock.lock();
        try {
            while (queue.size() == capcity) {
                try {
                    log.info("任务{}等待加入任务队列", element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.info("任务{}加入任务队列", element);
            queue.addLast(element);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    /*添加任务到队列的拒绝策略
    当队列满了之后【继续添加】会有如下情况
    1.死等（相当于直接调用put()方法）
    2.带超时的等待
    3.让调用者放弃
    4.让调用者抛出异常
    5.让调用者自己执行任务
    */
    public void tryPut(RejectPolicy<T> rejectPolicy, T element) {

        lock.lock();
        try {
            if (queue.size() == capcity) {
                rejectPolicy.reject(this, element);
            } else {
                log.info("任务{}加入任务队列", element);
                queue.addLast(element);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    //带超时间的添加
    public boolean offer(T element, long timeout, TimeUnit timeUnit) {

        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.size() == capcity) {
                try {
                    log.info("任务{}等待加入任务队列", element);
                    if (nanos <= 0) {
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.info("任务{}加入任务队列", element);
            queue.addLast(element);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    //获取队列大小
    public int queueSize() {

        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

}
