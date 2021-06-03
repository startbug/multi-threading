package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/1 22:57
 */
@Slf4j(topic = "c.WaitNotifyTest")
public class WaitNotifyTest {

    private static Object room = new Object();

    private static boolean hasCigarette = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有没有工具?{}", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没工具,摸鱼!");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有没有工具?{}", hasCigarette);
                if (hasCigarette) {
                    log.debug("工具到了,开工");
                }
            }
        }, "狗子").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("其他人打工!!");
                }
            }, "打工人").start();
        }

        TimeUnit.SECONDS.sleep(2);
        new Thread(() -> {
            synchronized (room) {
                hasCigarette = true;
                room.notify();
            }
        }, "送工具的").start();

    }
}
