package com.leolee.multithreadProgramming.threadPool.forkJoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @ClassName ForkJoinTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/3
 * @Version V1.0
 **/
public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();//无参的构造函数默认创建的是CPU核心数大小的线程池
        //分治的思想体现在：new MyTask(5) + new MyTask(4) + new MyTask(3) + new MyTask(2) + new MyTask(1)
        //System.out.println(forkJoinPool.invoke(new MyTask(5)));

        System.out.println(forkJoinPool.invoke(new MyTask(1, 5)));

    }

}

/*
 * 功能描述: <br>
 * 〈特殊的任务，并非是Thread或者是Runnable〉
 *  RecursiveTask是带返回结果的任务，RecursiveAction无返回结果
 * @Param:
 * @Return: 1-n之间整数的和
 * @Author: LeoLee
 * @Date: 2021/3/3 21:28
 */
@Slf4j
class MyTask extends RecursiveTask<Integer> {

    /*//问题版本示例使用
    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    //重写toString方便打印运算过程
    @Override
    public String toString() {
        return "MyTask{" +
                "n=" + n +
                '}';
    }


    //此过程就是类似于递归，
    //该示例存在的问题：虽然是不同的线程执行了各自的任务，但是每个线程都依赖于下一个线程的计算结果，并没有体现出多线程并行的特性
    @Override
    protected Integer compute() {
        //终止条件
        if (n == 1) {
            log.info("join() {}", n);
            return 1;
        }

        MyTask t1 = new MyTask(n - 1);
        t1.fork();//让一个线程去执行此任务
        log.info("fork() {} + {}", n, t1.toString());

        int t1Result = t1.join();//获取任务的执行结果
        int result = n + t1Result;
        log.info("join() {} + {} = {}", n, t1.toString(), result);
        return result;
    }*/


    //优化版本示例使用，高并行版本，有点类似于排序算法中的二分法
    private int begin;//开始的数字
    private int end;//结束的数字

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }

    @Override
    protected Integer compute() {

        //终止条件
        if (begin == end) {
            log.info("join() {}", begin);
            return begin;
        }
        if (end - begin == 1) {//这一段可写可不写，减少一次拆分而已
            log.info("join() {} + {} = {}", begin, end, begin + end);
            return begin + end;
        }

        int mid = (begin + end) / 2;

        MyTask t1 = new MyTask(begin, mid);
        t1.fork();
        MyTask t2 = new MyTask(mid + 1, end);
        t2.fork();
        log.info("fork() {} + {} = ?", t1.toString(), t2.toString());

        int result = t1.join() + t2.join();
        log.info("join() {} + {} = {}", t1, t2, result);

        return result;
    }

}
