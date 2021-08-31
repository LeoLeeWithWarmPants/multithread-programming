package com.leolee.multithreadProgramming.threadPool.customThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Test
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/2/24
 * @Version V1.0
 **/
@Slf4j
public class Test {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10, (queue, task) -> {
            /*当队列满了之后会有如下情况
            1.死等
            2.带超时的等待
            3.让调用者放弃
            4.让调用者抛出异常
            5.让调用者自己执行任务
            */
            //1.死等
//            queue.put(task);
            //带超时的等待
//            queue.offer(task, 500, TimeUnit.MILLISECONDS);
            //放弃任务
//            log.info("give up execute task:{}", task);
            //抛出异常
            throw new RuntimeException("a exception occurred during the task executing:" + task);
            //调用者自己执行任务
//            task.run();//相当于主线程在执行该任务
        });

        for (int i = 0; i < 15; i++) {
            int j = i;
            threadPool.execute(() -> {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                log.info("{}", j);
            });
        }
    }
}
