package com.leolee.multithreadProgramming.concurrent.order;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Test
 * @Description: 有序性，JVM指令重排序多线程测试
 * result的记过通常是1或者4，当多线程情况下，出现指令重排序的时候，可能会出现result=0的情况（可能性很小，但是还是会发生）
 * @Author LeoLee
 * @Date 2020/12/20
 * @Version V1.0
 **/
@Slf4j
public class Test {

    int num = 0;
    boolean ready = false;//使用volatile关键字修饰，可以解决指令重排的问题
    int result = 0;

    public void operation1() {
        if (ready) {
            result = num * 2;
        } else {
            result = 1;
        }
        log.info("{}", result);
    }

    public void operation2() {
        num = 2;
        ready = true;
    }

    public static void main(String[] args) {

        Test test = new Test();

        for (int i = 0; i < 500; i++) {
            if ((i % 2) == 0) {
                new Thread(() -> {
                    test.operation1();
                }).start();
            } else {
                new Thread(() -> {
                    test.operation2();
                }).start();
            }
        }
    }
}
