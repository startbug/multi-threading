package com.starbug.n5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 14:49
 */
@Slf4j(topic = "c.Test3")
public class Test3 {

    public static boolean flag = false;

    private static Object lock = new Object();

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);

        }, "t2");

        t1.start();
        t2.start();

    }

    public static void test1() {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("1");
                flag = true;
                lock.notifyAll();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                while (!flag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("2");
            }
        }, "t2").start();
    }

}
