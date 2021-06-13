package com.starbug.n5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 17:12
 *  使用park和unpark实现abcabcabcabc
 */
@Slf4j(topic = "c.Test6")
public class Test6 {

    private static Thread t1, t2, t3;

    public static void main(String[] args) {
        Park park = new Park(5);
        t1 = new Thread(() -> {
            park.print("a", t2);
        }, "t1");
        t2 = new Thread(() -> {
            park.print("b", t3);
        }, "t2");
        t3 = new Thread(() -> {
            park.print("c", t1);
        }, "t3");
        t1.start();
        t2.start();
        t3.start();

        System.out.println("开始");
        LockSupport.unpark(t3);
    }

}

class Park {

    private int loopNumber;

    public Park(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }

}
