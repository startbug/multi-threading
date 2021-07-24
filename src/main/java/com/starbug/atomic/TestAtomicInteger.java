package com.starbug.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Starbug
 * @Date 2021/7/24 22:15
 */
public class TestAtomicInteger {

    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(0);

        System.out.println(i.getAndIncrement());    // i++ 自增后返回自增前的值 0
        System.out.println(i.incrementAndGet());    // ++i 返回自增后的值      2

        System.out.println(i.getAndAdd(5)); //先获取,再+5
        System.out.println(i.addAndGet(5)); //先+5再获取值

        System.out.println(i.get());

    }

}
