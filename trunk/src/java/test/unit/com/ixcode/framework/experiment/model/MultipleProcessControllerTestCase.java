/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model;

import junit.framework.TestCase;

/**
 * TestCase for class : MultipleProcessController
 * Created     : Mar 31, 2007 @ 6:03:42 PM by jim
 */
public class MultipleProcessControllerTestCase extends TestCase {
    public void testConfigurationIndexes_2() {
            MultipleProcessController mp = new MultipleProcessController(1, 1);
            int[] indexes = mp.getConfigurationIndexes(6);
            assertEquals(new int[]{0, 1, 2, 3, 4, 5}, indexes);


        }


    public void testConfigurationIndexes_1() {
        MultipleProcessController mp = new MultipleProcessController(1, 4);
        int[] indexes = mp.getConfigurationIndexes(3);
        assertEquals(new int[]{0}, indexes);

        mp = new MultipleProcessController(2, 4);
        indexes = mp.getConfigurationIndexes(3);
        assertEquals(new int[]{1}, indexes);

        mp = new MultipleProcessController(3, 4);
        indexes = mp.getConfigurationIndexes(3);
        assertEquals(new int[]{2}, indexes);

        mp = new MultipleProcessController(4, 4);
        indexes = mp.getConfigurationIndexes(3);
        assertEquals(new int[]{}, indexes);

    }

    public void testConfigurationIndexes() {
        MultipleProcessController mp = new MultipleProcessController(1, 3);
        int[] indexes = mp.getConfigurationIndexes(5);
        assertEquals(new int[]{0}, indexes);


        mp = new MultipleProcessController(2, 3);
        indexes = mp.getConfigurationIndexes(5);
        assertEquals(new int[]{1}, indexes);

        mp = new MultipleProcessController(3, 3);
        indexes = mp.getConfigurationIndexes(5);
        assertEquals(new int[]{2, 3, 4}, indexes);

    }

    public void testConfigurationIndexes_longer() {
        MultipleProcessController mp = new MultipleProcessController(1, 3);
        int[] indexes = mp.getConfigurationIndexes(17);
        assertEquals(new int[]{0, 1, 2, 3, 4}, indexes);


        mp = new MultipleProcessController(2, 3);
        indexes = mp.getConfigurationIndexes(17);
        assertEquals(new int[]{5, 6, 7, 8, 9}, indexes);

        mp = new MultipleProcessController(3, 3);
        indexes = mp.getConfigurationIndexes(17);
        assertEquals(new int[]{10, 11, 12, 13, 14, 15, 16}, indexes);

    }


    public void testConfigurationIndexes_3() {
        MultipleProcessController mp = new MultipleProcessController(1, 6);
        int[] indexes = mp.getConfigurationIndexes(7);
        assertEquals(new int[]{0}, indexes);


        mp = new MultipleProcessController(2, 6);
        indexes = mp.getConfigurationIndexes(7);
        assertEquals(new int[]{1}, indexes);

        mp = new MultipleProcessController(3, 6);
        indexes = mp.getConfigurationIndexes(7);
        assertEquals(new int[]{2}, indexes);

        mp = new MultipleProcessController(4, 6);
        indexes = mp.getConfigurationIndexes(7);
        assertEquals(new int[]{3}, indexes);
        mp = new MultipleProcessController(5, 6);
        indexes = mp.getConfigurationIndexes(7);
        assertEquals(new int[]{4}, indexes);
        mp = new MultipleProcessController(6, 6);
        indexes = mp.getConfigurationIndexes(7);
        assertEquals(new int[]{5, 6}, indexes);


    }

    public void testConfigurationIndexes_4() {
            MultipleProcessController mp = new MultipleProcessController(1, 2);
            int[] indexes = mp.getConfigurationIndexes(8);
            assertEquals(new int[]{0, 1, 2, 3}, indexes);


            mp = new MultipleProcessController(2, 2);
            indexes = mp.getConfigurationIndexes(8);
            assertEquals(new int[]{4, 5, 6, 7}, indexes);



        }

    public static void assertEquals(int[] expected, int[] actual) {
        if (expected.length != actual.length) {
            fail("Lengths Not Equal: expected=" + expected.length + ", actual=" + actual.length);
        }
        for (int i = 0; i < expected.length; ++i) {
            assertEquals("[i]", expected[i], actual[i]);
        }
    }

}
