package com.leolee.multithreadProgramming.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName ArrayListTest
 * @Description: ArrayList的多线程不安全问题
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
public class ArrayListTest {

    public static void main(String[] args) {

        //运行异常java.util.ConcurrentModificationException
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            new Thread(() -> {
//                strings.add(UUID.randomUUID().toString());
//                System.out.println(strings);
//            }, String.valueOf(i)).start();
//        }

        //解决方式1，使用Vector,Vector的操作是加锁的
//        List<String> strings = new Vector<>();
//        for (int i = 0; i < 20; i++) {
//            new Thread(() -> {
//                strings.add(UUID.randomUUID().toString());
//                System.out.println(strings);
//            }, String.valueOf(i)).start();
//        }

        //解决方式2，使用Collections工具类
//        List<String> strings = Collections.synchronizedList(new ArrayList<>());
//        for (int i = 0; i < 20; i++) {
//            new Thread(() -> {
//                strings.add(UUID.randomUUID().toString());
//                System.out.println(strings);
//            }, String.valueOf(i)).start();
//        }

        //解决方式3，使用JUC CopyOnWriteArrayList
        List<String> strings = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString());
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }

    }

}
