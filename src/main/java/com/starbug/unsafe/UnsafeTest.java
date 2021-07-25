package com.starbug.unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author Starbug
 * @Date 2021/7/25 21:25
 */
public class UnsafeTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        //允许访问私有成员变量
        theUnsafe.setAccessible(true);
        //静态变量从属于类,不从属于对象,get的时候不需要传入对象,直接传入null即可
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

        //1.获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        //2.执行CAS操作
        //参数1: 操作的对象
        //参数2: 对象中字段的偏移地址
        //参数3: 期待值
        //参数4: 修改成的值
        unsafe.compareAndSwapInt(teacher, idOffset, 0, 1);
        unsafe.compareAndSwapObject(teacher, nameOffset, null, "李四");

        System.out.println(teacher);
    }

}

@Data
class Teacher {
    volatile int id;
    volatile String name;
}
