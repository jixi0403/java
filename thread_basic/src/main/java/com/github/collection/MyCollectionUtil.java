package com.github.collection;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Sunshine_wx on 17/4/12.
 */
public class MyCollectionUtil {

    public static void doSomething(Collection collection) {
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()) {
            Object object = iterator.next();
            System.out.println(object);
        }
    }
}
