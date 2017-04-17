package com.github.concurrentpractice.chapter_three;

/**
 * 通过对get和set方法进行同步
 */
public class SynchronizedInteger {
    private int value;

    public synchronized int get() {return value;}
    public synchronized void set(int value) {this.value = value;}
}
