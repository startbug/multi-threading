package com.starbug.status;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/9 20:51
 *  活锁,活锁出现在两个线程相互改变对方的结束条件,导致谁都无法结束
 */
@Slf4j(topic = "c.TestLiveLock")
public class TestLiveLock {

    private static volatile int count = 10;
    private static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            //期待减到0结束
            while (count > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                    count--;
                    log.debug("count:{}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            //期待加到20结束
            while (count < 20) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                    count++;
                    log.debug("count:{}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }

}
