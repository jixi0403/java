package com.github.demo.collection;

import com.github.collection.MyCollectionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
