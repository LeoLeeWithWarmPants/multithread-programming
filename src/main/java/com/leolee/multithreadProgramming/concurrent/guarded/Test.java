package com.leolee.multithreadProgramming.concurrent.guarded;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Test
 * @Description: 测试Gurarded
 * Gurarded的作用：
 *      如果有一个线程的结果需要传递到另一个线程，让他们用Gurarded做关联
 * JDK中join和future就是使用此模式实现的
 * @Author LeoLee
 * @Date 2020/12/5
 * @Version V1.0
 **/
@Slf4j
public class Test {

    /*
     * 功能描述: <br>
     * 〈基本测试〉
     * @Param:
     * @Return:
     * @Author: LeoLee
     * @Date: 2020/12/6 13:19
     */
    public void normalTest() {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            log.info("{} is waiting for response", Thread.currentThread().getName());
            log.info("response:{}", Boolean.valueOf(String.valueOf(guardedObject.getResponse())));
        }, "t1").start();

        new Thread(() -> {
            log.info("{} executing something for response", Thread.currentThread().getName());
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject.complete(true);
        }, "t2").start();
    }

    /*
     * 功能描述: <br>
     * 〈测试超时时间〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/6 13:22
     */
    public void testTimeout() {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            log.info("{} is waiting for response", Thread.currentThread().getName());
            log.info("response:{}", Boolean.valueOf(String.valueOf(guardedObject.getResponse(2*1000))));
        }, "t1").start();

        new Thread(() -> {
            log.info("{} executing something for response", Thread.currentThread().getName());
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject.complete(true);
        }, "t2").start();
    }


    /*
     * 功能描述: <br>
     * 〈测试优化之后的保护性暂停模式〉
     * @Param: []
     * @Return: void
     * @Author: LeoLee
     * @Date: 2020/12/6 15:46
     */
    public void testOptimized() {

        //开启三个线程等待结果
        for (int i = 1; i <= 3; i++) {
            new ReceiveThread().start();
        }

        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //发送结果
        for (Integer id : MiddleBox.getIds()) {
            new SendThread(id, "response[" + id + "]").start();
        }
    }

    public static void main(String[] args) {

        Test test = new Test();
        test.testOptimized();
    }
}

class GuardedObject {

    //之后传递的结果
    private Object response;

    //获取结果的方法
    public Object getResponse() {

        synchronized (this) {
            //response为空则进入等待，当有其他线程已经产生了结果并对response赋值之后，则唤醒该线程
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public Object getResponse(long timeout) {

        synchronized (this) {
            //开始等待的时间
            long beginTime = System.currentTimeMillis();
            //已经等待的时间
            long processTime = 0;

            //response为空则进入等待，当有其他线程已经产生了结果并对response赋值之后，则唤醒该线程
            while (response == null) {
                //防止虚假唤醒（就是防止其他线程唤起该线程的时候，response还没有值）
                //所以这里是等待timeout - processTime，即等待剩余没有等待的时间
                long waitTIme = timeout - processTime;
                if (waitTIme <= 0) {
                    break;
                }
                try {
                    this.wait(waitTIme);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                processTime = System.currentTimeMillis() - beginTime;
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response) {

        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }

}

//==========================优化===============================


class MiddleBox {

    private static Map<Integer, GuardedObject2> box = new Hashtable<>();

    private static volatile int id = 1;

    // static synchronized相当于对MiddleBox.class加锁
    private static synchronized int getId() {
        return id++;
    }

    public static GuardedObject2 createGuardedObject() {
        GuardedObject2 go = new GuardedObject2(getId());
        box.put(go.getId(), go);
        return go;
    }

    public static Set<Integer> getIds() {
        return box.keySet();
    }


    public static GuardedObject2 getGuardedObjectById(int id) {
        return box.remove(id);
    }

}

//拓展优化（解耦）
class GuardedObject2 {

    //标识
    private int id;

    //之后传递的结果
    private Object response;

    public GuardedObject2(int id) {
        this.id = id;
    }

    //获取结果的方法
    public Object getResponse() {

        synchronized (this) {
            //response为空则进入等待，当有其他线程已经产生了结果并对response赋值之后，则唤醒该线程
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public Object getResponse(long timeout) {

        synchronized (this) {
            //开始等待的时间
            long beginTime = System.currentTimeMillis();
            //已经等待的时间
            long processTime = 0;

            //response为空则进入等待，当有其他线程已经产生了结果并对response赋值之后，则唤醒该线程
            while (response == null) {
                //防止虚假唤醒（就是防止其他线程唤起该线程的时候，response还没有值）
                //所以这里是等待timeout - processTime，即等待剩余没有等待的时间
                long waitTIme = timeout - processTime;
                if (waitTIme <= 0) {
                    break;
                }
                try {
                    this.wait(waitTIme);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                processTime = System.currentTimeMillis() - beginTime;
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response) {

        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

//接受结果的线程
@Slf4j
class ReceiveThread extends Thread {

    @Override
    public void run() {
        GuardedObject2 go = MiddleBox.createGuardedObject();
        log.info("开始等待结果，id[{}]", go.getId());
        Object response = go.getResponse(10 * 1000);
        log.info("等待结束，id[{}]结果：{}", go.getId(), response);
    }
}

//发送结果的线程
@Slf4j
class SendThread extends Thread {

    private static int id;

    private String response;

    public SendThread(int id, String response) {
        this.id = id;
        this.response = response;
    }

    @Override
    public void run() {
        GuardedObject2 go = MiddleBox.getGuardedObjectById(id);
        log.info("开始发送结果,id[{}],response:{}", id, response);
        go.complete(response);
    }
}
