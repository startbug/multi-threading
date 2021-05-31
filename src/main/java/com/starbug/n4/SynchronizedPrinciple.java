package com.starbug.n4;

/**
 *  @Author Starbug
 *  @Date 2021/5/24 13:24
 *  反编译 javap -verbose SynchronizedPrinciple.class
 */
public class SynchronizedPrinciple {

    static final Object lock = new Object();

    static int counter = 0;

    public static void main(String[] args) {
        synchronized (lock) {
            counter++;
        }
    }

}
