/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

import java.math.BigDecimal;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BigDecimalMath {
    public static BigDecimal divide(BigDecimal numerator, int divisor) {
        return numerator.divide(accurateIn(divisor), ROUNDING);

    }

    public static BigDecimal divide(BigDecimal numerator, BigDecimal divisor) {
        return accurateOut(numerator.divide(divisor, ROUNDING));

    }

    public static BigDecimal calculateMean(BigDecimal[] values) {
        BigDecimal total = ZERO;
        for (int i = 0; i < values.length; ++i) {
            total = total.add(values[i]);
        }
        return accurateOut(total.divide(accurateIn(values.length), SCALE_IN, ROUNDING));
    }

    public static BigDecimal accurate(BigDecimal d) {
        return accurate(d, SCALE_OUT);
    }

    public static BigDecimal accurate(BigDecimal d, int scale) {
        return d.setScale(scale, ROUNDING);
    }

    public static BigDecimal accurateIn(double d) {
        return accurate(d, SCALE_IN);
    }

    public static BigDecimal accurateOut(double d) {
        return accurate(d, SCALE_OUT);
    }

    public static BigDecimal accurateOut(BigDecimal d) {
        return accurate(d, SCALE_OUT);
    }

    public static BigDecimal accurate(double d, int scale) {
//        String val = PRECISION_FORMAT.format(d);
        return accurate(new BigDecimal(d), scale);
    }

    public static final int SCALE_OUT = 9;
    public static final int SCALE_IN = 12; // make IN a bit longer so that we remove errors in calculations
    private static final int ROUNDING = BigDecimal.ROUND_UP; // prevents rounding errors i.e. means 9.9999999994 is actually 10.0
    public static final BigDecimal ZERO = accurateIn(0);
    public static final int SCALE = SCALE_IN;
    public static final BigDecimal MAX_BIG_DECIMAL = accurateIn(Double.MAX_VALUE);
}
