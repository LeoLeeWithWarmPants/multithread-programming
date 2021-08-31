package com.leolee.multithreadProgramming.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName SetTest
 * @Description: Set集合线程不安全问题
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
public class SetTest {

    public static void main(String[] args) {

//        HashSet<String> strings = new HashSet<>();
//        for (int i = 0; i < 20; i++) {
//            new Thread(() -> {
//                strings.add(UUID.randomUUID().toString());
//                System.out.println(strings);
//            }, String.valueOf(i)).start();
//        }

        //解决方式1，Collections
//        Set<String> strings = Collections.synchronizedSet(new HashSet<>());
//        for (int i = 0; i < 20; i++) {
//            new Thread(() -> {
//                strings.add(UUID.randomUUID().toString());
//                System.out.println(strings);
//            }, String.valueOf(i)).start();
//        }

        //解决方法2
        Set<String> strings = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString());
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }
}
