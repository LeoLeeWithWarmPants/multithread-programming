package com.leolee.multithreadProgramming.test.synCodeSnippet;

/**
 * @ClassName Task
 * @Description: 测试同步代码块
 * @Author LeoLee
 * @Date 2020/9/1
 * @Version V1.0
 **/
public class Task {

    private String getData1;

    private String getData2;


    /**
     * 功能描述: <br> 同步方法
     * 〈〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/9/1 16:57
     */
    public synchronized void doLongTimeTask() {

        try {
            System.out.println("task begin");
            Thread.sleep(3000);
            String getData1 = "长时间处理任务后返回值[1],threadName=" + Thread.currentThread().getName();
            String getData2 = "长时间处理任务后返回值[2],threadName=" + Thread.currentThread().getName();

            System.out.println("data1:" + getData1);
            System.out.println("data2:" + getData2);
            System.out.println("task end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 功能描述: <br> 解决对象锁占用时间过长的问题
//     * 〈〉
//     * @Param: []
//     * @Return: void
//     * @Author: LeoLee
//     * @Date: 2020/9/1 16:57
//     */
//    public void doLongTimeTask() {
//
//        try {
//            System.out.println("task begin");
//
//            Thread.sleep(3000);
//            String data1Temp = "长时间处理任务后返回值[1],threadName=" + Thread.currentThread().getName();
//            String data2Temp = "长时间处理任务后返回值[2],threadName=" + Thread.currentThread().getName();
//
//            syn (this) {
//                getData1 = data1Temp;
//                getData2 = data2Temp;
//            }
//
//            System.out.println("data1:" + getData1);
//            System.out.println("data2:" + getData2);
//            System.out.println("task end");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
