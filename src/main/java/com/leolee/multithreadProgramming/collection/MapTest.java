package com.leolee.multithreadProgramming.collection;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MapTest
 * @Description: Map的多线程安全问题
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
public class MapTest {

    public static void main(String[] args) {

        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                concurrentHashMap.put(Thread.currentThread().getName(), UUID.randomUUID().toString());
                System.out.println(concurrentHashMap);
            }, String.valueOf(i)).start();
        }
    }
}
