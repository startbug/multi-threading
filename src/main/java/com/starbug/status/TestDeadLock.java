package com.starbug.status;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/9 20:30
 */
@Slf4j(topic = "c.TestDeadLock")
public class TestDeadLock {
    public static Object A = new Object();
    public static Object B = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                log.debug("获得锁A...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    log.debug("获得锁B...");
                    log.debug("其他操作...");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                log.debug("获得锁A...");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    log.debug("获得锁B...");
                    log.debug("其他操作...");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
