package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 15:28
 */
@Slf4j(topic = "c.Test4")
public class Test4 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                //sleep wait join会重制打断标记,所以打断后标记为false
                //可以通过不抓InterruptException异常,修改标识为true
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();

        log.debug("interrupt...");

        TimeUnit.MILLISECONDS.sleep(500);
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
    }

}
