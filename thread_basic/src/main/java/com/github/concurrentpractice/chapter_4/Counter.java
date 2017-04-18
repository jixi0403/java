package com.github.concurrentpractice.chapter_4;

/**
 * 设计线程安全类要素
 * 1 找出构成对象状态的所有变量
 * 2 找出约束状态变量的不变性条件
 * 3 建立对象状态的并发访问管理策略
 */
public final class Counter {
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if(value == Integer.MAX_VALUE) {
            throw new IllegalStateException("counter overflow");
        }

        return ++value;
    }
}
