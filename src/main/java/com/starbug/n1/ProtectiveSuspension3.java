package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/6 21:54
 *  邮递员与用户一一对应
 */
@Slf4j(topic = "c.ProtectiveSuspension3")
public class ProtectiveSuspension3 {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i < 3; i++) {
            new Person().start();
        }

        TimeUnit.SECONDS.sleep(1);

        for (Integer id : MailBoxes.getIds()) {
            new Postman("内容-" + id, id).start();
        }
    }
}

@Slf4j(topic = "c.Person")
class Person extends Thread {

    @Override
    public void run() {
        GuardedObject3 guardedObject = MailBoxes.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(30000);
        log.debug("信id: {},信内容: {}", guardedObject.getId(), mail);
    }

}

@Slf4j(topic = "c.Postman")
class Postman extends Thread {

    private String mail;

    private int id;

    public Postman(String mail, int id) {
        this.mail = mail;
        this.id = id;
    }

    @Override
    public void run() {
        GuardedObject3 guardedObject = MailBoxes.getGuardedObject(id);
        log.debug("信id: {},信内容: {}", id, mail);
        guardedObject.complete(mail);
    }

}

class MailBoxes {
    //多线程访问,需要使用线程安全的集合
    private static Map<Integer, GuardedObject3> boxes = new Hashtable();

    private static int id = 0;

    private static synchronized int generatedId() {
        return id++;
    }

    public static GuardedObject3 createGuardedObject() {
        GuardedObject3 guardedObject = new GuardedObject3(generatedId());
        boxes.put(guardedObject.getId(), guardedObject);
        return guardedObject;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    //收信后,将其删除
    public static GuardedObject3 getGuardedObject(int id) {
        return boxes.remove(id);
    }

}

//增加超时效果
class GuardedObject3 {

    private int id;

    public GuardedObject3(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //结果
    private Object response;

    //获取结果
    public Object get(long timeout) {
        synchronized (this) {
            long base = System.currentTimeMillis();    //记录最开始的时间戳
            long delay = 0;  //记录已经用了多少时间

            while (response == null) {
                //已花费时间>=用户设置的超时时间,则结束循环
                if (delay >= timeout) {
                    break;
                }
                try {
                    //如果线程在等待的时候被错误唤醒(没到超时时间就被唤醒)
                    //判断如果还是没有数据,则继续等待,但是等待的时间需要减去之前已经花费的时间
                    this.wait(timeout - delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                delay = System.currentTimeMillis() - base;  //记录已花费的时间
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response) {
        synchronized (this) {
            //产生结果给成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}
