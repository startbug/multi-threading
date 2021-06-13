package com.starbug.n5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 16:54
 *  使用ReentrantLock实现顺序打印abcabcabcabcabc
 */
@Slf4j(topic = "c.Test5")
public class Test5 {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();
        AwaitSignal awaitSignal = new AwaitSignal(lock, 30);

        new Thread(() -> {
            awaitSignal.print("a", a, b);
        }, "t1").start();

        new Thread(() -> {
            awaitSignal.print("b", b, c);
        }, "t2").start();

        new Thread(() -> {
            awaitSignal.print("c", c, a);
        }, "t3").start();

        TimeUnit.SECONDS.sleep(1);
        try {
            lock.lock();
            System.out.println("开始!!!!!");
            b.signal();
        } finally {
            lock.unlock();
        }

    }

}

class AwaitSignal {

    private ReentrantLock lock;

    private int loopNumber;

    public AwaitSignal(ReentrantLock lock, int loopNumber) {
        this.lock = lock;
        this.loopNumber = loopNumber;
    }

    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            try {
                lock.lock();
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}


