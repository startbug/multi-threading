package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/7 23:02
 */
@Slf4j(topic = "c.MessageQueueTest")
public class MessageQueueTest {

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                messageQueue.put(new Message(id, "值" + id));
            }, "生产者" + id).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message take = messageQueue.take();
            }
        }, "消费者").start();

    }

}

//消息队列类,线程之间通信
@Slf4j(topic = "c.MessageQueueTest")
class MessageQueue {
    //消息队列集合
    private LinkedList<Message> messagesQueue = new LinkedList();
    //容量
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    //获取消息
    public Message take() {
        synchronized (messagesQueue) {
            while (messagesQueue.isEmpty()) {
                try {
                    messagesQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = messagesQueue.removeFirst();
            log.debug("从队列中获取消息{}--{}", message.getId(), message.getValue());
            messagesQueue.notifyAll();
            return message;
        }
    }

    //存入消息
    public void put(Message message) {
        synchronized (messagesQueue) {
            while (messagesQueue.size() >= capacity) {
                try {
                    log.debug("队列已满,等待中...{}--{}", message.getId(), message.getValue());
                    messagesQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入队列中{}--{}", message.getId(), message.getValue());
            messagesQueue.addLast(message);
            messagesQueue.notifyAll();
        }
    }

}

final class Message {

    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }
}
