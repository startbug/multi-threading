package com.starbug.five;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Starbug
 * @Date 2021/7/21 23:52
 */
public class AccountTest {

    public static void main(String[] args) {
        Account account1 = new AccountUnsafe(10000);
        Account account2 = new AccountCAS(10000);
        Account.demo(account1);
        Account.demo(account2);
    }

}

class AccountCAS implements Account {

    private AtomicInteger balance;

    public AccountCAS(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        while (true) {
//            //获取原有的余额值
//            int prev = this.balance.get();
//            //计算扣减后的余额值
//            int next = prev - amount;
//            if (this.balance.compareAndSet(prev, next)) {
//                break;
//            }
//        }
        //优化: 使用addAndGet,设置负数表示扣减存款
        this.balance.addAndGet(-amount);
    }
}

class AccountUnsafe implements Account {

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public synchronized Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        this.balance -= amount;
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

