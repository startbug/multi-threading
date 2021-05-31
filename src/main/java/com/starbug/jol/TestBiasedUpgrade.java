package com.starbug.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 *  @Author Starbug
 *  @Date 2021/5/30 15:17
 *  偏向锁升级为轻量级锁
 *  取消偏向锁延迟
 */
@Slf4j(topic = "c.TestBiasedUpgrade")
public class TestBiasedUpgrade {

    public static void main(String[] args) {
        Monkey monkey = new Monkey();

        new Thread(() -> {
            log.debug(ClassLayout.parseInstance(monkey).toPrintable());
            synchronized (monkey) {
                log.debug(ClassLayout.parseInstance(monkey).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(monkey).toPrintable());

            synchronized (TestBiasedUpgrade.class) {
                TestBiasedUpgrade.class.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (TestBiasedUpgrade.class) {
                try {
                    TestBiasedUpgrade.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(ClassLayout.parseInstance(monkey).toPrintable());
            synchronized (monkey) {
                log.debug(ClassLayout.parseInstance(monkey).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(monkey).toPrintable());
        }, "t2").start();

    }

}

class Monkey {

}
