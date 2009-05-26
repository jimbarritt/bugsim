/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.math;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 30, 2007 @ 6:21:12 PM by jim
 */
public class IntegerMath {

    public static int maxOf(int[] values) {
        int max = 0;
        for (int i=0;i<values.length;++i) {
            if (values[i]>max) {
                max = values[i];
            }
        }
        return max;
    }

    public static int minOf(int[] values) {
        int min = 0;
        for (int i=0;i<values.length;++i) {
            if (values[i]>min) {
                min = values[i];
            }
        }
        return min;
    }
}
