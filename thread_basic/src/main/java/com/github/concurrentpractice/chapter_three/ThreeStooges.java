package com.github.concurrentpractice.chapter_three;

import java.util.HashSet;
import java.util.Set;

/**
 * 在可变对象基础上构建不可变类
 */
public class ThreeStooges {

    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}
