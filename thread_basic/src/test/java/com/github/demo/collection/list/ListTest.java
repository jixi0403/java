package com.github.demo.collection.list;

import org.junit.Test;

import java.util.*;

/**
 * Created by Sunshine_wx on 17/4/12.
 */
public class ListTest {

    @Test
    public void testListNew() {
        List listA = new ArrayList();
        List listB = new LinkedList();
        List listC = new Vector();
        List listD = new Stack();
    }

    @Test
    public void testAddingAndAccessing() {
        List listA = new ArrayList();
        listA.add("element 1");
        listA.add("element 2");
        listA.add("element 3");
        listA.add(0, "element 0");

        listA.get(0);

        Iterator iterator = listA.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for(Object object : listA) {
            System.out.println(object);
        }
    }
}
