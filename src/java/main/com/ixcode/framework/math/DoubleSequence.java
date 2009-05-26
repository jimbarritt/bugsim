/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

/**
 *  Description : contains a sequence of numbers
 */
public class DoubleSequence  {

    public DoubleSequence(double from, double to, double by, double precision) {
        _range = new DoubleRange(from, to, precision);
        _sequence = DoubleMath.createSequenceDouble(from, to,  by, precision);
        _by = by;
    }

    public DoubleRange getRange() {
        return _range;
    }

    public double[] getSequence() {
        return _sequence;
    }

    public double getPrecision() {
        return _range.getPrecision();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DoubleSequence that = (DoubleSequence)o;

        if (Double.compare(that._by, _by) != 0) return false;
        if (_range != null ? !_range.equals(that._range) : that._range != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        long temp;
        result = (_range != null ? _range.hashCode() : 0);
        temp = _by != +0.0d ? Double.doubleToLongBits(_by) : 0L;
        result = 29 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }

    public double getBy() {
        return _by;
    }

    /**
     * looks for the index of this key but does it based on the "range" od the keys.
     *
     * SO if the range is by 1 and goes 0, 1, 2, 3, 4
     *
     * 0.5 -> 0
     * 0.51 -> 1
     * 1.5 ->  1
     * 1.51 -> 2
     * 2.5 -> 2
     *
     * etc.
     *
     * if wraparound then anything greatere than the last slot is added to the first one.
     *
     *
     * @param key
     * @return
     */
    public int findIndexOfKey(double key, boolean wraparound) {
        int foundIndex=(wraparound) ? 0 : -1;  //if its 
        for (int i=0;i<_sequence.length;++i) {
            double slotMid = _sequence[i];
            double slotMin = slotMid-(_by/2);
            double slotMax = slotMid+(_by/2);
            if (DoubleMath.precisionGreaterThan(key, slotMin, _range.getPrecision()) && DoubleMath.precisionLessThanEqual(key, slotMax, _range.getPrecision())) {
                foundIndex = i;
                break;
            }
        }

        return foundIndex;
    }

    private DoubleRange _range;

    private double[] _sequence;

    private double _by;
}
