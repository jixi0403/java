package com.github.demo.collection.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Sunshine_wx on 17/4/13.
 */
public class StreamTest {

    @Test
    public void testListStream() {
        List<String> items = new ArrayList<String>();
        items.add("one");
        items.add("two");
        items.add("three");

        Stream<String> stream = items.stream();
    }
}
