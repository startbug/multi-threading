package com.starbug.n3;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/21 13:24
 *  jstack PID抓取线程状况
 */
public class Testtt {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }

}
