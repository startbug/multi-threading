package com.starbug.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author Starbug
 * @Date 2021/7/25 21:46
 */
public class UnsafeAccessor {

    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }

}
