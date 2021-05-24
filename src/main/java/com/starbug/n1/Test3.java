package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 15:17
 */
@Slf4j(topic = "c.Test3")
public class Test3 {

    static int r = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        log.debug("开始");

        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("流批");
            r = 10;
        }, "t1");

        t1.start();
        //join(), 等待t1线程执行完
        t1.join();
        log.debug("结果为{}", r);
        log.debug("结果");


    }

}
