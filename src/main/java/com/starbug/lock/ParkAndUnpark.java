package com.starbug.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *  @Author Starbug
 *  @Date 2021/6/8 13:30
 *  特点:
 *      与Object的wait&notify相比
 *
 */
@Slf4j(topic = "c.ParkAndUnpark")
public class ParkAndUnpark {

    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[]{2, 4, 4, 3, 5};
        int[] arr2 = {2, 4, 3534, 2, 34, 234, 234};
        Thread t1 = new Thread(() -> {
            log.debug("start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("park");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        TimeUnit.SECONDS.sleep(2);
        log.debug("unpark....");
        LockSupport.unpark(t1);
    }

}
