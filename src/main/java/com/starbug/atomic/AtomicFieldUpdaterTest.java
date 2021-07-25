package com.starbug.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author Starbug
 * @Date 2021/7/25 16:44
 * 字段更新器
 */
public class AtomicFieldUpdaterTest {

    public static void main(String[] args) {
        Student stu = new Student();
        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        boolean success = updater.compareAndSet(stu, null, "李四");
        System.out.println(success);
        System.out.println(stu);
    }

}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
