package com.starbug.test;

import lombok.extern.slf4j.Slf4j;

/**
 *  @Author Starbug
 *  @Date 2021/5/20 22:32
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            log.debug("running");
        });
        thread.setName("t1");
        thread.start();

        log.debug("running");
    }

}
