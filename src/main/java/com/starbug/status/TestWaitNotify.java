package com.starbug.status;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/9 20:02
 */
@Slf4j(topic = "c.TestWaitNotify")
public class TestWaitNotify {

    private static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("等待中....");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("唤醒,执行...");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                try {
                    log.debug("等待中....");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("唤醒,执行...");
            }
        }, "t2").start();

        new Thread(() -> {
            synchronized (lock) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    lock.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "其他").start();

    }

}
