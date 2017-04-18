package com.github.concurrentpractice.chapter_three;

/**
 * 有问题的对象发布
 * 调用者可以随意修改数组的内容，私有变量被发布出去
 */
public class UnsafeStates {
    private String[] states = new String[]{"AK", "AL"};

    public String[] getStates() {
        return states;
    }
}
