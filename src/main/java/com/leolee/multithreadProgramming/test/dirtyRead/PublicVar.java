package com.leolee.multithreadProgramming.test.dirtyRead;

/**
 * @ClassName PublicVar
 * @Description: 脏读
 * @Author LeoLee
 * @Date 2020/8/27
 * @Version V1.0
 **/
public class PublicVar {

    public String userName;

    public String password;

    public PublicVar(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void getValue() {
        System.out.println("getValue method thread Name:" + Thread.currentThread().getName());
        System.out.println("userName:" + this.userName + ",password:" + this.password);
    }

    public synchronized void setValue(String userName, String password) {
        try {
            this.userName = userName;
            Thread.sleep(5000);
            this.password = password;
            System.out.println("setValue method thread Name:" + Thread.currentThread().getName());
            System.out.println("userName:" + this.userName + ",password:" + this.password);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
