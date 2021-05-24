package com.starbug.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 18:06
 */
@Slf4j(topic = "c.Test2")
public class Test2 {

    static int THREAD_NUMBER = 200;
    static int LOOP_NUMBER = 2000;

    public static void main(String[] args) {
        ThreadSafeSub threadSafe = new ThreadSafeSub();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                threadSafe.method1(LOOP_NUMBER);
            }, "t" + i).start();
        }

    }

}

class ThreadSafe {

    public void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    public void method2(ArrayList list) {
        list.add("1");
    }

    public void method3(ArrayList list) {
        list.remove(0);
    }

}

class ThreadSafeSub extends ThreadSafe {
    @Override
    public void method3(ArrayList list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}