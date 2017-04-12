package com.github.demo.collection;

import com.github.collection.MyCollectionUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Sunshine_wx on 17/4/12.
 */
public class MyCollectionUtilTest {

    @Test
    public void testDoSomething() {
        Set set = new HashSet();
        List list = new ArrayList();

        MyCollectionUtil.doSomething(set);
        MyCollectionUtil.doSomething(list);
    }

    @Test
    public void testAddAndRemove() {
        String aElement = "an element";
        Collection collection = new HashSet();
        boolean didCollectionChange = collection.add(aElement);
        Assert.assertTrue(didCollectionChange);
        boolean wasElementRemoved = collection.remove(aElement);
        Assert.assertTrue(wasElementRemoved);
    }

    @Test
    public void testCollectionSize() {
        Collection set = new HashSet();
        set.add("a");

        Assert.assertEquals(1, set.size());
    }

    @Test
    public void testNewLoop() {
        Collection set = new HashSet();
        set.add("a");

        for(Object object : set) {
            System.out.println(object);
        }
    }

}
