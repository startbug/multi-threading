package com.starbug.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

/**
 *  @Author Starbug
 *  @Date 2021/5/30 16:45
 *  批量重偏向
 */
@Slf4j(topic = "c.BatchReBiased")
public class BatchReBiased {

    public static void main(String[] args) {
        Vector<Dragon> list = new Vector();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Dragon dragon = new Dragon();
                list.add(dragon);
                synchronized (dragon) {
                    log.debug((i + "\t" + ClassLayout.parseInstance(dragon).toPrintable()));
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("=========================================================================================");
            for (int i = 0; i < 30; i++) {
                Dragon dragon = list.get(i);
                log.debug((i + "\t" + ClassLayout.parseInstance(dragon).toPrintable()));
                synchronized (dragon) {
                    log.debug((i + "\t" + ClassLayout.parseInstance(dragon).toPrintable()));
                }
                log.debug((i + "\t" + ClassLayout.parseInstance(dragon).toPrintable()));
            }
        }, "t2");
        t2.start();

    }

}


class Dragon {

}
