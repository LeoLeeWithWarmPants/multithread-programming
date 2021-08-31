package com.leolee.multithreadProgramming.threadPool.customThreadPool;

/*
 * 功能描述: <br>
 * 〈拒绝策略〉用于处理当队列慢之后的行为
 * 1.死等
 * 2.带超时的等待
 * 3.让调用者放弃
 * 4.让调用者抛出异常
 * 5.让调用者自己执行任务
 * @Param:
 * @Return:
 * @Author: LeoLee
 * @Date: 2021/2/25 10:22
 */

@FunctionalInterface
public interface RejectPolicy<T> {

    void reject(BlockingQueue blockingQueue, T task);
}
