package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/22 23:59
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();

        log.debug("running....{}", t1.getState());

        TimeUnit.MILLISECONDS.sleep(500);

        log.debug("running....{}", t1.getState());
    }

}
