package com.starbug.n6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 18:39
 *  内存可见性测试
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    private static boolean run = true;

    private static Object lock = new Object();
//
//    public static void main(String[] args) throws InterruptedException {
//        new Thread(() -> {
//            while (true) {
//                synchronized (lock) {
//                    if (!run) {
//                        break;
//                    }
//                }
//            }
//        }).start();
//
//        TimeUnit.SECONDS.sleep(1);
//
//        log.debug("停止t");
//        synchronized (lock) {
//            run = false;
//        }
//    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (run) {
                System.out.println(run);
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        log.debug("停止t");
        run = false;
    }

}
