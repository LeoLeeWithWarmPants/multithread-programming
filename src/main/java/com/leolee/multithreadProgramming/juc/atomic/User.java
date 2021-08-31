package com.leolee.multithreadProgramming.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName User
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/3/1
 * @Version V1.0
 **/
@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {

    private String name;

    private int age;

}
