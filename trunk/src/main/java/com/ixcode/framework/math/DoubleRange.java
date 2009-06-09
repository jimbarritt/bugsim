/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

/**
 *  Description : implements a range of numbers - i.e has a start and end
 */
public class DoubleRange {


    public DoubleRange(double start, double end, double precision) {
        _start = start;
        _end = end;
        _precision = precision;
    }

    public DoubleRange changePrecision(double precision) {
        return new DoubleRange(_start, _end, precision);
    }

    public boolean isInRangeInclusive(double x) {
        return DoubleMath.precisionBetweenInclusive(_start, x, _end, _precision);
    }

    public boolean isInRangeExclusive(double x) {
        return DoubleMath.precisionBetweenExclusive(_start, x, _end, _precision);
    }

    public double getFrom() {
        return _start;
    }

    public double getTo() {
        return _end;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DoubleRange that = (DoubleRange)o;

        if (Double.compare(that._end, _end) != 0) return false;
        if (Double.compare(that._precision, _precision) != 0) return false;
        if (Double.compare(that._start, _start) != 0) return false;

        return true;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = _start != +0.0d ? Double.doubleToLongBits(_start) : 0L;
        result = (int)(temp ^ (temp >>> 32));
        temp = _end != +0.0d ? Double.doubleToLongBits(_end) : 0L;
        result = 29 * result + (int)(temp ^ (temp >>> 32));
        temp = _precision != +0.0d ? Double.doubleToLongBits(_precision) : 0L;
        result = 29 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }

    public double getPrecision() {
        return _precision;
    }



    private double _start;
    private double _end;

    private double _precision;

}
