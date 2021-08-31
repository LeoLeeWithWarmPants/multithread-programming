package com.leolee.multithreadProgramming.collection.blockingQueue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BlockingQueueTest
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/2
 * @Version V1.0
 **/
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueueTest.arrayBlockingQueue();
    }

    public static void arrayBlockingQueue() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);//队列大小3

        //暴力存取，会报异常
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));//继续添加元素将会报错

        //检查队首元素
        System.out.println(blockingQueue.element());//返回队首元素，如果队列中没有元素将会报错

        //取元素，先进先出
        System.out.println(blockingQueue.remove());//a
        System.out.println(blockingQueue.remove());//b
        System.out.println(blockingQueue.remove());//c
//        System.out.println(blockingQueue.remove());//队列中没有元素的话，将会报错

        System.out.println("---------------------------------------------------------------");

        //温柔存取，不会报异常
        System.out.println(blockingQueue.offer("1"));
        System.out.println(blockingQueue.offer("2"));
        System.out.println(blockingQueue.offer("3"));
        System.out.println(blockingQueue.offer("4"));//将会返回false

        //检查队首元素
        System.out.println(blockingQueue.peek());//返回1，队列为空也不会报错而是返回null

        //取元素，先进先出
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//队列中没有元素返回null

        System.out.println("---------------------------------------------------------------");

        //阻塞存取
        blockingQueue.put("1");
        blockingQueue.put("2");
        blockingQueue.put("3");
//        blockingQueue.put("4");//当队列已满之后的put操作将会阻塞直到队列存在可用空间

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());//当队列为空的时候会阻塞，知道队列中有新的元素可以被取出

        System.out.println("---------------------------------------------------------------");

        //带超时时间的存取
        System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d", 2, TimeUnit.SECONDS));//指挥阻塞2秒钟，超时后将会返回false

        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));//返回null
    }
}
