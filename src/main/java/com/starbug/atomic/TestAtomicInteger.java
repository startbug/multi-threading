package com.starbug.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Starbug
 * @Date 2021/7/24 22:15
 */
public class TestAtomicInteger {

    public static void main(String[] args) {
        AtomicInteger i1 = new AtomicInteger(0);

        System.out.println(i1.getAndIncrement());    // i++ 自增后返回自增前的值 0
        System.out.println(i1.incrementAndGet());    // ++i 返回自增后的值      2

        System.out.println(i1.getAndAdd(5)); //先获取,再+5
        System.out.println(i1.addAndGet(5)); //先+5再获取值
        System.out.println(i1.get());

        System.out.println("========================111111111=========================");

        AtomicInteger i2 = new AtomicInteger(2);
        //返回计算后的结果(设置新的值会使用CAS判断,不断自旋,直到成功)
        int sufResult = i2.updateAndGet(value -> value * 5);
        System.out.println(sufResult);  //10

        //返回计算前的值(设置新的值会使用CAS判断,不断自旋,直到成功)
        int preResult = i2.getAndUpdate(value -> value - 5);
        System.out.println(preResult);
        System.out.println(i2.get());

        AtomicInteger i = new AtomicInteger(0);

        //pre修改前的值,x为第一个传入的参数10
        System.out.println(i.getAndAccumulate(10, (pre, x) -> pre + x));
        System.out.println(i.accumulateAndGet(10, (pre, x) -> pre + x));
    }

}
