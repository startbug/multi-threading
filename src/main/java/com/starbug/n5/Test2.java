package com.starbug.n5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 14:30
 */
@Slf4j(topic = "c.Test2")
public class Test2 {

    public static ReentrantLock ROOM = new ReentrantLock();

    public static Condition waitToolSet = ROOM.newCondition();
    public static Condition waitTakeoutSet = ROOM.newCondition();

    public static boolean hasTool = false;
    public static boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                ROOM.lock();
                while (!hasTool) {
                    log.debug("没有工具...等吧");
                    try {
                        waitToolSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("拿到工具,开工{}", hasTool);
            } finally {
                ROOM.unlock();
            }
        }, "t1").start();

        new Thread(() -> {

            try {
                ROOM.lock();
                while (!hasTakeout) {
                    log.debug("等外卖...");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了,干饭{}", hasTakeout);
            } finally {
                ROOM.unlock();
            }

        }, "t2").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            try {
                ROOM.lock();
                hasTakeout = true;
                waitTakeoutSet.signalAll();
            } finally {
                ROOM.unlock();
            }
        }, "employee").start();


    }

}
