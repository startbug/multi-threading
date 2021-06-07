package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/6 20:21
 *  wait中的线程代码需要使用while循环,不能使用if,因为if只能进行因此判断,而while可以一直判断
 */
@Slf4j(topic = "c.WaitNotifyTest2")
public class WaitNotifyTest2 {

    private static final Object room = new Object();
    private static boolean hasTool = false;
    private static boolean hasTakeout = false;

    public static void main(String[] args) {
        //虚假唤醒,错误的唤醒了在WaitSet等待的线程
        new Thread(() -> {
            synchronized (room) {
                log.debug("有没有工具?{}", hasTool);
                while (!hasTool) {
                    log.debug("没工具,摸鱼!");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有没有工具?{}", hasTool);
                if (hasTool) {
                    log.debug("开始干活");
                } else {
                    log.debug("没工具,停工");
                }
            }
        }, "小明").start();

        new Thread(() -> {
            synchronized (room) {
                log.debug("外卖呢?{}", hasTakeout);
                while (!hasTakeout) {
                    log.debug("等等外卖");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖呢?{}", hasTakeout);
                if (hasTakeout) {
                    log.debug("外卖到了,干饭");
                } else {
                    log.debug("饿死了");
                }
            }
        }, "当代大学生").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (room) {
                log.debug("外卖到了啊");
                hasTakeout = true;
//                room.notify();
                room.notifyAll();
            }
        }).start();

//        System.out.println("-------------------------------------------");
//        System.out.println(hasTakeout);
//        System.out.println(!hasTakeout);
//
//        hasTakeout = !hasTakeout;
//        System.out.println(hasTakeout);
    }

}
