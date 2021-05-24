package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 15:59
 */
@Slf4j(topic = "c.Test7")
public class Test7 {

    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    private static void test3() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            log.debug("park....");
            //让当前线程停下来
            LockSupport.park();
            log.debug("unpark...");

//            log.debug("打断状态:{}", Thread.currentThread().isInterrupted());   //当前为true
            log.debug("打断状态:{}", Thread.interrupted());   //调用时为true,之后会重制,设置为false

            //当打断状态为true,则在调用park会无效
            LockSupport.park();
            log.debug("unpark...");

        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        t1.interrupt();
    }

}
