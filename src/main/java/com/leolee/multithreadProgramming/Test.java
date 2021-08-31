package com.leolee.multithreadProgramming;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName Test
 * @Description: JVM测试需要-XX:+PrintGCDetails
 * @Author LeoLee
 * @Date 2020/12/1
 * @Version V1.0
 **/
@Slf4j
public class Test {


    public void mutableTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.info("{}", sdf.parse("2020-02-12"));
                } catch (ParseException e) {
                    e.printStackTrace();
                    log.error("{}", e);
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.mutableTest();
    }
}

