/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LongMath {
    public static long minOf(long value1, long value2) {
        return (value1 > value2) ? value1 : value2;
    }

    public static double mean(long[] times) {
        double sum=0;
        int len = times.length;
        for (int i=0;i<len;++i) {
            sum+=times[i];
        }
        return sum/len;
    }
}
