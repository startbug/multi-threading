package com.starbug.n5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 14:20
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Condition n1 = lock.newCondition();
        Condition n2 = lock.newCondition();

        try {
            lock.lock();
            //要使用Condition休息室,必须先获得锁,类似于wait
            //当前线程进入等待
            n1.await();

            //唤醒n1休息室中的某一个线程
            n1.signal();
            //唤醒n1休息室中所有线程
            n1.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

}
