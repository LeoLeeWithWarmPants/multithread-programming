package com.leolee.multithreadProgramming.concurrent.threadSafetyAnalyses;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ThreadSafetyTest
 * @Description: 线程安全的分析（变量的线程安全）
 * @Author LeoLee
 * @Date 2020/11/30
 * @Version V1.0
 **/
@Slf4j
public class ThreadSafetyTest {

    //=========================成员变量线程不安全分析==============================

    static class MemberVariableUnsafe {
        List<String> list = new ArrayList<String>();

        public void method1(int loopNum) {
            for (int i = 0; i < loopNum; i++) {
                method2();
                method3();
            }
        }

        public void method2() {
            list.add("222");
        }

        public void method3() {
            list.remove(0);
        }
    }

    //=========================局部变量线程安全分析==============================

    /*
     * 功能描述: <br>
     * 〈i是局部变量，每个线程调用方法test1的时候，会在每个线程的栈帧中创建多份，因此不存在线程安全问题〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/11/30 21:03
     */
    public static void test1() {
        int i = 10;
        i++;
    }

    static class LocalVariableSafe {

        public void method1(int loopNum) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < loopNum; i++) {
                method2(list);
                method3(list);
            }
        }

        public void method2(List<String> list) {
            list.add("222");
        }

        public void method3(List<String> list) {
            list.remove(0);
        }
    }

    //=========================局部变量发生逃离，线程安全分析==============================

    static class LocalVariableSubClass extends LocalVariableSafe {

        @Override
        public void method3(List<String> list) {
            new Thread(() -> {
                list.remove(0);
            }).start();
        }
    }

    public static void main(String[] args) {
        MemberVariableUnsafe memberVariableUnsafe = new MemberVariableUnsafe();
        log.info("=========================成员变量线程不安全分析==============================");
        /*for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                memberVariableUnsafe.method1(50);
            }, "Thread-" + i).start();
        }*/

        log.info("=========================局部变量线程安全分析==============================");
        /*LocalVariableSafe localVariableSafe = new LocalVariableSafe();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                localVariableSafe.method1(50);
            }, "Thread-" + i).start();
        }*/

        log.info("=========================局部变量发生逃离，线程安全分析==============================");
        LocalVariableSubClass localVariableSubClass = new LocalVariableSubClass();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                localVariableSubClass.method1(50);
            }, "Thread-" + i).start();
        }
    }
}
