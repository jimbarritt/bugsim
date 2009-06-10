package com.ixcode.framework.math;

import com.ixcode.framework.math.random.*;
import org.junit.*;import static org.junit.Assert.assertEquals;import static org.junit.Assert.fail;

import java.util.*;

public class PoissonRandomGeneratorTest {

    private static final Random RANDOM = new Random();

    @Test
    public void testPoisson() {

        double mu = 4d;
        final int N = 10000;
        Map results = new HashMap();
        for (int i = 0; i < N; ++i) {
            Integer p = new Integer(PoissonRandom.generatePoissonRandom(RANDOM, mu));
            if (!results.containsKey(p)) {
                results.put(p, new Integer(0));
            }
            Integer freq = (Integer)results.get(p);
            results.put(p, new Integer(freq.intValue() + 1));
        }

        System.out.println("p, frequency");
        List sortedKeys = new ArrayList(results.keySet());
        Collections.sort(sortedKeys);
        long total = 0;
        for (Iterator itr = sortedKeys.iterator(); itr.hasNext();) {
            Integer key = (Integer)itr.next();
            Integer freq = (Integer)results.get(key);
            System.out.println(key + "," + freq);
            total += freq.intValue();
        }

        assertEquals("total", N, total);
    }

    @Test
    public void fails() {
        fail("bang!");
    }
}
