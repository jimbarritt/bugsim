/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DoubleMath {
    public static boolean equals(double d1, double d2) {
        return Math.abs(d1 - d2) <= DOUBLE_PRECISION_DELTA;
    }

    /**
     * COmputes wether a double value is between two limits taking in to account that
     * it might be equal to the limits and that because its a double, when it is right on the limit the values might vary
     * because of double variation.
     * <p/>
     * <p/>
     * e.g. 100 <= 99.990 <=101
     * <p/>
     * between will return true if you pass a delta of 1e-1 but false if you pass 1e-n where n > 1
     * <p/>
     * 1e-1 = 0.1
     * 1e-2 = 0.01
     * <p/>
     * It works out what the difference is between the two values and checks to see if it
     * <p/>
     * For the standard accuracy of the system use DOUBLE_DELTA
     *
     * @param lower
     * @param x
     * @param upper
     * @param delta
     * @return
     */
    public static boolean precisionBetweenInclusive(double lower, double x, double upper, double delta) {
        return precisionLessThanEqual(lower, x, delta) && precisionLessThanEqual(x, upper, delta);
    }

    /**
     * Is x1 less than or equal to x2 if you want the standard accuracy for the sim use DOUBLE_DELTA
     *
     * @param x1
     * @param x2
     * @return
     */
    public static boolean precisionLessThanEqual(double x1, double x2, double delta) {
        return x1 < x2 || precisionEquals(x1, x2, delta);
    }

    public static boolean precisionEquals(double x1, double x2, double delta) {
        return (Math.abs(x1 - x2) <= delta);
    }

    public static boolean precisionGreaterThanEqual(double x1, double x2, double delta) {
        return x1 > x2 || precisionEquals(x1, x2, delta);
    }


    /**
     * COmputes wether a double value is between two limits taking in to account that
     * it might be equal to the limits and that because its a double, when it is right on the limit the values might vary
     * because of double variation.
     * <p/>
     * <p/>
     * e.g. 100 < 99.990 < 101
     * <p/>
     * between will return true if you pass a delta of 1e-1 but false if you pass 1e-n where n > 1
     * <p/>
     * 1e-1 = 0.1
     * 1e-2 = 0.01
     * <p/>
     * It works out what the difference is between the two values and checks to see if it
     * <p/>
     * For the standard accuracy of the system use DOUBLE_DELTA
     *
     * @param lower
     * @param x
     * @param upper
     * @param delta
     * @return
     */
    public static boolean precisionBetweenExclusive(double lower, double x, double upper, double precision) {
        return precisionGreaterThan(x, lower, precision) && precisionLessThan(x, upper, precision);
    }

    /**
     * @todo work out what to do here - it needs to be less than but taking into account the precision - maybe add the precision ?
     * @param x
     * @param x2
     * @param precision
     * @return
     */
    public static boolean precisionLessThan(double x, double x2, double precision) {
        return x < x2;
    }

    /**
     * * @todo work out what to do here - it needs to be less than but taking into account the precision - maybe subtract the precision ?
     * @param x
     * @param x2
     * @param precision
     * @return
     */
    public static boolean precisionGreaterThan(double x, double x2, double precision) {
        return x > x2;
    }

    public static double[] createSequenceDouble(int from, int to, int by) {

            int sequenceCount = (int)(Math.abs((to-from)/by)) + 1; // add one to make it inclusive
            double[] sequence = new double[sequenceCount];//not sure about this but should work

            double current = (double)from;
            for (int i = 0; i < sequenceCount; ++i) {
                sequence[i] = current;
                current += by;
            }
            return sequence;
        }

    public static double[] createSequenceDouble(double from, double to, double by, double precision) {
        double current = from;
        int sequenceCount = (int)(Math.abs((to-from)/by)) + 1; // add one to make it inclusive
        double[] sequence = new double[sequenceCount];
        for (int i =0; i<sequenceCount;i++) {
            sequence[i]= current;
            current+=by;
        }
        return sequence;
    }

    public static double maxOf(double[] data) {
        double max = 0;
        for (int i = 0; i < data.length; ++i) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    public static double minOf(double[] data) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

    /**
     * Simple rounding function
     *
     * @param value
     * @param scale
     * @return
     */
    public static double roundDouble(double value, double scale) {
        double powerOfTen = Math.pow(10, scale);
        return Math.round(value * powerOfTen) / powerOfTen;
    }

    public static double roundDouble(double value) {
        return Math.round(value * DOUBLE_PRECISION_ROUNDING) / DOUBLE_PRECISION_ROUNDING;
    }

    private static double[] convertToPrimitiveDoubleArray(List inputValues) {
        double[] results = new double[inputValues.size()];
        int i = 0;
        for (Iterator itr = inputValues.iterator(); itr.hasNext();) {
            Double value = (Double)itr.next();
            results[i++] = value.doubleValue();
        }
        return results;
    }

    public static String format(double value) {
        return DECIMAL_FORMAT.format(value);
    }

    /**
     * Given a number, x tells you what the inclusive Square Root would be of it.
     * <p/>
     * e.g. for 5, it would be 3 (3 x 3 = 6)
     * 10             4 (4 x 4 = 16)
     * <p/>
     * Tells you how big to make a square to fit in all the number of items if they are spread out in even numbers
     * of rows and columns (like a table)
     *
     * @param x
     * @return integer which represents this
     */
    public static int inclusiveSqrt(double x) {
        double sqrtPoints = Math.sqrt(x);
        double mod = sqrtPoints % 1;
        return (int)((mod == 0) ? sqrtPoints : (sqrtPoints - mod) + 1);
    }

    public static DecimalFormat createFormatter(int decimalPlaces) {
        StringBuffer pattern = new StringBuffer();
        pattern.append("0");
        if (decimalPlaces > 0) {
            pattern.append(".");
            for (int i = 0; i < decimalPlaces; ++i) {
                pattern.append("0");
            }
        }
        return new DecimalFormat(pattern.toString());
    }

    public static int sign(double x) {
        int sign = 0;
        if (x <0) {
            sign = -1;
        } else if (x>0) {
            sign = 1;
        }
        return sign;
    }

    /**
     * A Scale of 9 should be sufficient this would be 1 mm if the landscape scale was 1 unit == 100km
     * 100km == 100,000 m
     * = 10,000,000 cm
     * = 100, 000, 000 mm
     * 1 / 100 = 0.01
     * <p/>
     * 1 / 100, 000, 000 == 0.00000001
     * <p/>
     * 1mm = 0.000 000 01 if the sclae is 1 unit == 100km this is a scale of 8
     * <p/>
     * So we set the scale to be 9 to give an extra degree of accuracy. this means that if someone passes in a random double,
     * <p/>
     * say 0.000 000 01 it will actually be a double looking like 0.000 000 01 4676376476 ( loads of random garbage)
     * <p/>
     * by chosing a scale 1 greater than we need the worse that can happen is that this always gets rounded up to 1.
     * Therefore as long as someone passes in the input properly scaled :
     * 0.000 000 010  if it then gets put into a double, and back into a scaled the worst error would be :
     * 0.000 000 011
     * <p/>
     * of course we need to go through and change everything to BigDecimals!!
     */
    public static final double DOUBLE_PRECISION_DELTA = 5e-7;
    public static final long DOUBLE_PRECISION_ROUNDING = 7;
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000000"); // 1e-6 which is the maximum precision we support accurately
}
