package com.starbug.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 21:16
 */
@Slf4j(topic = "c.MoneyTransfer")
public class MoneyTransfer {


    public static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        Bank b1 = new Bank(1000);
        Bank b2 = new Bank(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b1.transfer(b2, randomMoney());
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b2.transfer(b1, randomMoney());
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(b1.getMoney() + b2.getMoney());
    }

    //0-100
    public static int randomMoney() {
        return random.nextInt(100) + 1;
    }

}

class Bank {

    private int money;

    public Bank(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void transfer(Bank target, int transfer) {
        synchronized (Bank.class) {
            if (transfer <= money) {
                this.setMoney(this.money - transfer);
                target.setMoney(target.getMoney() + transfer);
            }
        }
    }

}



