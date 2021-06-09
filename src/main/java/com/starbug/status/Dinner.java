package com.starbug.status;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/9 20:40
 */
@Slf4j(topic = "c.Dinner")
public class Dinner {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("刀剑神域", c1, c2).start();
        new Philosopher("电磁炮", c2, c3).start();
        new Philosopher("鬼灭之刃", c3, c4).start();
        new Philosopher("命运石之门", c4, c5).start();
        new Philosopher("进击的巨人", c5, c1).start();
    }

}


@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread {

    private Chopstick left;

    private Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    private void eat() {
        try {
            log.debug("我吃饭先...");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


class Chopstick {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
