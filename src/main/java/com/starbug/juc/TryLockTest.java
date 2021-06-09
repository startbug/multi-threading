package com.starbug.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  @Author Starbug
 *  @Date 2021/6/9 22:10
 */
@Slf4j(topic = "c.TryLockTest")
public class TryLockTest {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("获取锁失败");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                //如果等待锁过程中被打断,就会被catch
                //没得到锁,就不应该继续执行
                return;
            }
            try {
                log.debug("获得锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        t1.start();
        log.debug("主线程获得锁");
        lock.lock();
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        log.debug("主线程释放锁");

    }

}
