package com.leolee.multithreadProgramming.juc.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @ClassName Test
 * @Description: Semaphore信号量
 * 主要作用：
 *      1.用于多个共享资源的互斥使用
 *      2.控制并发线程数
 * @Author LeoLee
 * @Date 2020/12/4
 * @Version V1.0
 **/
@Slf4j
public class Test {

    /*
     * 功能描述: <br>
     * 〈Semaphore 的一般使用〉
     * 使用抢车位的场景，车多车位少
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/4 15:56
     */
    public void semaphoreTest() {

        Semaphore semaphore = new Semaphore(3);
        //有6辆车来抢车位
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    //Semaphore的3会-1，即一个资源被占用了(acquire方法是占用一个资源，如果没有占用到资源将一直等待下去)
                    semaphore.acquire();
                    log.info("Thread{}占用了一个停车位", Thread.currentThread().getName());
                    //三秒钟之后车就开走了，即释放资源
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放资源,+1
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }

    public static void main(String[] args) {

        Test test = new Test();
        test.semaphoreTest();
    }
}
