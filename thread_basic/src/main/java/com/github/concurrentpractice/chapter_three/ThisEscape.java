package com.github.concurrentpractice.chapter_three;

/**
 * this指针逸出
 * 在构造函数中显示或隐式启动一个线程，this指针会被新创建的线程共享，
 * 在对象为完全构造之前，新的线程可以看到它（this）。
 *
 * 在构造函数中创建线程并没有什么问题，但是在构造函数未完全完成之前，最好不要
 * 启动它，而是通过start或initialize方法来启动
 */
public class ThisEscape {

    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent() {
                System.out.println("Do something.");
            }
        });
    }
}

class EventSource {

    public void registerListener(EventListener listener) {

    }
}

interface EventListener {
    void onEvent();
}
