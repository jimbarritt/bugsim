/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

import junit.framework.TestCase;

/**
 * TestCase for class : DoubleSequence
 */
public class DoubleSequenceTestCase extends TestCase {


    public void testFindKey() {
        DoubleSequence seq = new DoubleSequence(0, 10, 1, DoubleMath.DOUBLE_PRECISION_DELTA);

        for (int i = 0;i<=10;++i) {
            runFindKeyTest(seq, i+ 0.0001, i);
            runFindKeyTest(seq, i+0.5, i);

            if (i==10) {
                runFindKeyTest(seq, i+0.50001, 0);
            }
        }




    }

    private void runFindKeyTest(DoubleSequence seq, double key, int expectedIndex) {
        int actual = seq.findIndexOfKey(key, true);
        assertEquals("index: key=" + key + ", expected=" + expectedIndex, expectedIndex, actual);
    }
}
