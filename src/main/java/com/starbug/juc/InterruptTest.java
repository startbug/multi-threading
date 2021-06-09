package com.starbug.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  @Author Starbug
 *  @Date 2021/6/9 21:52
 */
@Slf4j(topic = "c.InterruptTest")
public class InterruptTest {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                //如果没有竞争,那么此方法将获得lock锁
                //如果有竞争就进入阻塞队列中,与lock不同的是,这个等待锁的过程可以都interrupt打端
                log.debug("尝试获取锁....");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等待获取锁被打断...");
                return;
            }
            try {
                log.debug("获得到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");


        lock.lock();
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("打断等待锁...");
        thread.interrupt();
    }

}
