package com.github.concurrentpractice.chapter_three;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Sunshine_wx on 17/4/18.
 */
public class OneValueCache {

    private final BigInteger lastNumber;
    private final BigInteger[] lastFactory;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        this.lastNumber = i;
        this.lastFactory = factors;
    }

    public BigInteger[] getFactors(BigInteger i) {
        if(lastNumber == null || !lastNumber.equals(i)) {
            return null;
        } else {
            return Arrays.copyOf(lastFactory, lastFactory.length);
        }
    }
}
