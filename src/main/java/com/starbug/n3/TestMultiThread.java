package com.starbug.n3;

import lombok.extern.slf4j.Slf4j;

/**
 *  @Author Starbug
 *  @Date 2021/5/21 13:14
 *  tasklist
 *  taskkill /F /PID 18932      杀死进程
 *
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t1").start();
        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t2").start();
    }

}
