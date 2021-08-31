package com.leolee.multithreadProgramming.immutable.pool;

import java.sql.Connection;
import java.util.Random;

/**
 * @ClassName Test
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/1/13
 * @Version V1.0
 **/
public class Test {

    public static void main(String[] args) {
        CustomizedPool customizedPool = new CustomizedPool(3);
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                Connection connection = customizedPool.getConnection();
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                customizedPool.free(connection);
            }).start();
        }
    }
}
