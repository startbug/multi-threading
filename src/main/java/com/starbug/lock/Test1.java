package com.starbug.lock;

import lombok.extern.slf4j.Slf4j;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 17:05
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    static int counter = 0;
    static Object local = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (local) {
                for (int i = 0; i < 1000; i++) {
                    counter--;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (local) {
                for (int i = 0; i < 1000; i++) {
                    counter++;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("{}", counter);
    }

}
