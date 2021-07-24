package com.starbug.atomic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author Starbug
 * @Date 2021/7/24 22:34
 */
public class AccountTest {

    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountCAS(new BigDecimal("10000")));
        String s = "a" + "b" + "c";
        String s2 = "a" + "bc";
        System.out.println(s == s2);
    }

}

class DecimalAccountCAS implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCAS(BigDecimal balance) {
        this.balance = new AtomicReference(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        BigDecimal pre, next;
        do {
            pre = balance.get();
            next = pre.subtract(amount);
        } while (!balance.compareAndSet(pre, next));
    }
}

interface DecimalAccount {

    //获取余额
    BigDecimal getBalance();

    void withdraw(BigDecimal amount);

    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }

        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(account.getBalance());
    }
}