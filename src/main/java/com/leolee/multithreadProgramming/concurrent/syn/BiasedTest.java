package com.leolee.multithreadProgramming.concurrent.syn;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName BiasedTest
 * @Description: 偏向锁
 * @Author LeoLee
 * @Date 2020/12/1
 * @Version V1.0
 **/
@Slf4j
public class BiasedTest {

    class Person {

    }

    /*
     * 功能描述: <br>
     * 〈验证偏向锁对象头的mark word信息，需要借助jol-core包〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/1 15:47
     */
    public void testBiasedMarkword() throws InterruptedException {
        Person person = new Person();
        //偏向锁有延迟性，并不会在程序启动后马上生效，所以sleep，也可以添加VM参数： -XX:BiasedLockingStartupDelay=0 来禁用延迟
        //可以使用-XX:-UseBiasedLocking 禁用偏向锁，此时该对象加锁后会编程 000
        //Thread.sleep(5*1000);

        //获取hashcode后导致偏向锁失效
        log.info(String.valueOf(person.hashCode()));

        log.info(ClassLayout.parseInstance(person).toPrintable());
        synchronized (person) {
            log.info(ClassLayout.parseInstance(person).toPrintable());
        }
        log.info(ClassLayout.parseInstance(person).toPrintable());
    }

    /*
     * 功能描述: <br>
     * 〈测试多个线程访问同一个对象，偏向锁被撤销〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/1 16:57
     */
    public void testBiasedLockUnusable() {

        Person person = new Person();

        Thread t1 = new Thread(() -> {
            log.info(ClassLayout.parseInstance(person).toPrintable());
            synchronized (person) {
                log.info(ClassLayout.parseInstance(person).toPrintable());
            }
            log.info(ClassLayout.parseInstance(person).toPrintable());
        }, "t1");

        Thread t2 = new Thread(() -> {

            try {
                //保证t2在t1执行完成之后再执行
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info(ClassLayout.parseInstance(person).toPrintable());
            synchronized (person) {
                log.info(ClassLayout.parseInstance(person).toPrintable());
            }
            log.info(ClassLayout.parseInstance(person).toPrintable());
        }, "t2");

        t1.start();
        t2.start();
    }

    public static void main(String[] args) throws InterruptedException {
        BiasedTest biasedTest = new BiasedTest();
        //biasedTest.testBiasedMarkword();
        biasedTest.testBiasedLockUnusable();
    }
}
