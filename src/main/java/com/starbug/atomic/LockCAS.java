package com.starbug.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Starbug
 * @Date 2021/7/25 19:25
 * 手动写一个锁,生产环境禁止这样操作
 */
@Slf4j(topic = "c.LockCAS")
public class LockCAS {

    //标志位
    //0 没加锁
    //1 加锁
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        while (true) {
            //加锁成功,退出循环,否则一致空转
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        log.debug("unlock...");
        state.set(0);
    }

    public static void main(String[] args) {
        LockCAS lock = new LockCAS();

        new Thread(() -> {
            log.debug("1--begin...");
            lock.lock();
            try {
                log.debug("1--lock...");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            log.debug("2--begin...");
            lock.lock();
            try {
                log.debug("2--lock...");
            } finally {
                lock.unlock();
            }
        }).start();
    }

}
