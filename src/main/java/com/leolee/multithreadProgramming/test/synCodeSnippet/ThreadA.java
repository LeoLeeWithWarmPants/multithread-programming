package com.leolee.multithreadProgramming.test.synCodeSnippet;

/**
 * @ClassName ThreadA
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/9/1
 * @Version V1.0
 **/
public class ThreadA extends Thread {

    private Task task;

    public ThreadA(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        TimeTempUtils.beginTime1 = System.currentTimeMillis();
        task.doLongTimeTask();
        TimeTempUtils.endTime1 = System.currentTimeMillis();
    }
}
