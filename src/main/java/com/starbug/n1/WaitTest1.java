package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/1 13:12
 *  wait是Object对象中的方法
 *  wait只能在同步代码块中执行(必须要先获得锁才可以调用wait(才可以进入到WaitSet中))
 *  在同步代码块中调用wait,当前线程会进入到锁对象的Monitor中的WaitSet中等待,并释放锁(调用wait方法,都会升级成重量级锁)
 *
 */
@Slf4j(topic = "c.WaitTest1")
public class WaitTest1 {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("执行...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码....");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("执行...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码....");
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("唤醒obj上其他线程");
        synchronized (lock) {
//            lock.notify();    //随机唤醒在lock对象的monitor的WaitSet中waiting的一条线程
            lock.notifyAll();   //唤醒所有在lock对象的monitor的WaitSet中waiting的线程
        }
    }
}
