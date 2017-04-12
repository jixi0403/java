package com.github.demo.collection.set;

import org.junit.Test;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Sunshine_wx on 17/4/12.
 */
public class TreeSetTest {
    
    @Test
    public void testCreateTreeSet() {
        SortedSet set = new TreeSet(new MyComparator());
    }
}

class MyComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        return 0;
    }
}
