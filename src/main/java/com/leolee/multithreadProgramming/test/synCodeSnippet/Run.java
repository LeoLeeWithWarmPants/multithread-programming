package com.leolee.multithreadProgramming.test.synCodeSnippet;

/**
 * @ClassName Run
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/9/1
 * @Version V1.0
 **/
public class Run {

    public static void main(String[] args) {

        Task task = new Task();
        ThreadA threadA = new ThreadA(task);
        threadA.setName("AAA");
        threadA.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadB threadB = new ThreadB(task);
        threadB.setName("BBB");
        threadB.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long beginTime = TimeTempUtils.beginTime1;
        if (TimeTempUtils.beginTime2 < TimeTempUtils.beginTime1) {
            beginTime = TimeTempUtils.beginTime2;
        }
        long endTime = TimeTempUtils.endTime1;
        if (TimeTempUtils.beginTime2 > TimeTempUtils.beginTime1) {
            endTime = TimeTempUtils.endTime2;
        }

        System.out.println("时间差：" + ((endTime - beginTime) / 1000));
    }
}
