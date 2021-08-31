package com.leolee.multithreadProgramming.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName FutureTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/11/29
 * @Version V1.0
 **/
@Slf4j//和下面的日志声明功能相同（使用的方法是：log.info等）
public class FutureTest {

    static final Logger logger = LoggerFactory.getLogger(FutureTest.class);

    public static void main(String[] args) {
        logger.info("test log");
    }
}
