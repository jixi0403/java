package com.github.concurrentpractice.chapter_three;

/**
 * 非线程安全
 * 可能会获取到失效值
 * 如果某个线程调用了set，那么另一个正在调用get的线程可能会看到更新
 * 后的值，也可能看不到
 */
public class MutableInteger {

    private int value;

    public int get() {return value;}
    public void set(int value) {
        this.value = value;
    }
}
