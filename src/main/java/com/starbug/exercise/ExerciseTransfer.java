package com.starbug.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 20:43
 */
@Slf4j(topic = "c.ExerciseTransfer")
public class ExerciseTransfer {
    public static final Random r = new Random();

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Integer> list = new ArrayList();
        ArrayList<Thread> threadList = new ArrayList();

        TicketWindow ticketWindow = new TicketWindow(200);
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                int sell = 0;
                try {
                    sell = ticketWindow.sell(randomAmount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(sell);
                System.out.println(sell);
            });
            thread.start();
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        int actualCount = list.stream().filter(i -> i != null).reduce(Integer::sum).orElse(0);
        System.out.println(actualCount);
    }

    public static int randomAmount() {
        return r.nextInt(5) + 1;
    }

}


class TicketWindow {

    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public synchronized int sell(int amount) throws InterruptedException {
        if (amount <= count) {
            TimeUnit.MILLISECONDS.sleep(10);
            count -= amount;
            return amount;
        } else {
            return 0;
        }
    }

}
