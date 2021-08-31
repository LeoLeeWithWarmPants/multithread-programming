package com.leolee.multithreadProgramming.concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName FutureTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/11/29
 * @Version V1.0
 **/
@Slf4j
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Object> futureTask = new FutureTask<Object>((Callable) () -> {
            log.info("futureTask executing,currentThreadName:" + Thread.currentThread().getName());
            Thread.sleep(3*1000);
            return true;
        });

        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(Boolean.valueOf(String.valueOf(futureTask.get())));
    }
}
