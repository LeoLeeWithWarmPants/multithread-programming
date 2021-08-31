package com.leolee.multithreadProgramming.test.sysNotExtentds;

/**
 * @ClassName Run
 * @Description: 同步不具有继承性
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class Run {

    public static void main(String[] args) {
        Sub sub = new Sub();
        ThreadA a = new ThreadA(sub);
        a.setName("a");
        a.start();
        ThreadB b = new ThreadB(sub);
        b.setName("b");
        b.start();
    }
}
