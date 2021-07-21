package com.starbug.single;

/**
 * @Author Starbug
 * @Date 2021/7/21 23:15
 * 单例模式--饿汉式
 * <p>
 * 1.为什么添加final?
 * 放置方法被覆盖,破坏单例
 * <p>
 * 2.如果实现了序列化接口,怎么做才可以防止反序列化破坏单例?
 * 添加readResolve方法,返回当前单例对象即可
 * 原因: ObjectInputStream#2134行中的Object rep = desc.invokeReadResolve(obj);,通过反射调用readResolve方法,如果有对象返回,则序列化成返回的对象
 * <p>
 * 3.为什么构造方法设置成私有的?
 * 防止new创建对象
 * <p>
 * 4.这样初始化是否能保证单例对象创建时的线程安全?
 * 可以,jvm加载类是就会创建,只创建一次
 * <p>
 * 5.为什么不把INSTANCE设置成public的?
 * 方便扩展,提供反省支持等等
 */
public class Singleton1 {

    private static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    public Object readResolve() {
        return INSTANCE;
    }
}
