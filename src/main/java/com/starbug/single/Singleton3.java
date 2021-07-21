package com.starbug.single;

/**
 * @Author Starbug
 * @Date 2021/7/21 23:48
 */
public final class Singleton3 {

    private Singleton3() {
    }

    //懒汉式,当没有到成员变量的时候,不会加载改类,也就是不会创建该对象
    private static class LazyHolder {
        static final Singleton3 INSTANCE = new Singleton3();
    }

    //jvm在加载的时候创建对象,无并发问题
    public Object getInstance() {
        return LazyHolder.INSTANCE;
    }

}
