package com.leolee.multithreadProgramming.concurrent.messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @ClassName Test
 * @Description: 消息队列模式：java的线程之间通信的消息队列
 * JDK中各种阻塞队列就是使用的是该模式
 * @Author LeoLee
 * @Date 2020/12/6
 * @Version V1.0
 **/
@Slf4j
public class Test {

    public static void test() {
        MesssageQueue messsageQueue = new MesssageQueue(2);

        for (int i = 0; i < 3; i++) {
            int n = i;
            new Thread(() -> {
                messsageQueue.put(new Message(n, "值" + n));
            }, "生产者" + i).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messsageQueue.take();
            }
        }, "消费者").start();
    }

    public static void main(String[] args) {
        Test.test();
    }
}

@Slf4j
class MesssageQueue {

    //使用有序的链表来充当队列
    private LinkedList<Message> list = new LinkedList<>();

    //队列容量
    private int capacity;

    public MesssageQueue(int capacity) {
        this.capacity = capacity;
    }

    //获取消息
    public Message take() {
        //检查消息队列是否为空
        synchronized (list) {
            while (list.isEmpty()) {
                log.info("当前队列为空，消费者线程等待");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //从头部取出消息并返回
            Message message = list.removeFirst();
            //同时通知所有等待存入的线程（但是实际上是唤醒所有的线程，包括等待获取消息的线程）
            log.info("消息被消费");
            list.notifyAll();
            return message;
        }
    }

    //存入消息
    public void put(Message message) {
        synchronized (list) {
            //检查队列是否已经满了
            while (list.size() == capacity) {
                log.info("当前队列已满，生产者线程等待");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //消息加入队列的尾部
            list.add(message);
            log.info("消息被放入队列,{}", message.toString());
            //通知所有等待获取消息的线程（但是实际上是唤醒所有的线程，包括等待存在的线程）
            list.notifyAll();
        }
    }

}

@Slf4j
final class Message {

    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
