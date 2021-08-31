package com.leolee.multithreadProgramming.test.synCodeSnippet;

/**
 * @ClassName ThreadB
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/9/1
 * @Version V1.0
 **/
public class ThreadB extends Thread {

    private Task task;

    public ThreadB(Task task) {
        super();
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        TimeTempUtils.beginTime2 = System.currentTimeMillis();
        task.doLongTimeTask();
        TimeTempUtils.endTime2 = System.currentTimeMillis();
    }
}
