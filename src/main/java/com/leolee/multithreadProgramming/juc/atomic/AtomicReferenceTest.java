package com.leolee.multithreadProgramming.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName AtomicReferenceTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
public class AtomicReferenceTest {

    public static void main(String[] args) {
        User leo = new User("Leo", 26);
        User jerry = new User("Jerry", 20);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(leo);
        System.out.println(atomicReference.compareAndSet(leo, jerry) + "\t" + atomicReference.get().toString());
    }

}
