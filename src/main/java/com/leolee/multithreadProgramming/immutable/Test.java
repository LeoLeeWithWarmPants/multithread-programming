package com.leolee.multithreadProgramming.immutable;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @ClassName Test
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/1/13
 * @Version V1.0
 **/
@Slf4j
public class Test {

    /*
     * 功能描述: <br>
     * 〈可变类的举例〉可变类的线程安全需要用锁来保护
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2021/1/13 10:30
     */
    public void mutableTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (sdf) {
                    try {
                        log.info("{}", sdf.parse("2020-02-12"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        log.error("{}", e);
                    }
                }
            }).start();
        }
    }

    /*
     * 功能描述: <br>
     * 〈不可变类DateTimeFormatter〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2021/1/13 10:31
     */
    public void dateTimeFormatterTest() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                TemporalAccessor temporalAccessor = dtf.parse("2020-02-12");
                log.info("{}", temporalAccessor.toString());
            }).start();
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.mutableTest();
    }
}
