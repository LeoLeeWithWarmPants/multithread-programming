package com.leolee.multithreadProgramming.test.objectVariate;

/**
 * @ClassName Run
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/8/24
 * @Version V1.0
 **/
public class Run {

    public static void main(String[] args) {
        MyObject object = new MyObject();
        ThreadA a = new ThreadA(object);
        a.setName("A");
        ThreadB b = new ThreadB(object);
        b.setName("B");
        a.start();
        b.start();
    }
}
