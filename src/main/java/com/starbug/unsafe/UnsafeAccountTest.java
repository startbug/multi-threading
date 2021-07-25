package com.starbug.unsafe;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Starbug
 * @Date 2021/7/25 21:58
 */
@Slf4j(topic = "c.UnsafeAccountTest")
public class UnsafeAccountTest {

    public static void main(String[] args) {
        //1000个线程，每个线程-10元，1w元减到0元
        Account.demo(new MyAtomicInteger(10000));
    }

}


class MyAtomicInteger implements Account {
    private volatile int value;
    public static final long valueOffset;

    static final Unsafe UNSAFE;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            //获取域的偏移量
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        int pre, next;
        do {
            pre = this.value;
            next = pre - amount;
        } while (!UNSAFE.compareAndSwapInt(this, valueOffset, pre, next));
    }


    @Override
    public Integer getBalance() {
        return this.getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}

interface Account {

    //获取金额
    Integer getBalance();

    //取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动1000个线程,每个线程执行-10元的操作,
     * 如果初始余额为1000,那么正确结果应该为0
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }

        long start = System.nanoTime();

        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost: " + (end - start) / 1000_000 + " ms");
    }

}

