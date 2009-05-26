/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math;

import com.ixcode.framework.math.stats.SummaryStatistics;
import com.ixcode.framework.javabean.format.DoubleFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DiscreetValueMap {


    public DiscreetValueMap(double from, double to, double by, double precision) {
        _keys = new DoubleSequence(from, to, by, precision);
        _values = new double[_keys.getSequence().length];

        _keyList = new ArrayList();
        for (int i = 0; i < _keys.getSequence().length; ++i) {
            _keyList.add(new Double(_keys.getSequence()[i]));
        }

        _valueList = new ArrayList();
        for (int i = 0; i < _values.length; ++i) {
            _valueList.add(new Double(_values[i]));
        }
    }

    public DiscreetValueMap copyStructure() {
        return new DiscreetValueMap(getFrom(), getTo(), getBy(), getPrecision());
    }

    public int getIndexOfKey(double key) {
        return _keyList.indexOf(new Double(key));
    }

    public double getKey(int index) {
        return _keys.getSequence()[index];
    }

    public double getValue(double key) {
        return getValue(getIndexOfKey(key));
    }

    public double getValue(int index) {
        return _values[index];
    }

    public void setValue(int index, double value) {
        _values[index] = value;
    }

    public double[] getKeys() {
        return _keys.getSequence();
    }

    public int getSize() {
        return _keys.getSequence().length;
    }

    public double[] getValues() {
        return _values;
    }

    public double getPrecision() {
        return _keys.getPrecision();
    }

    public double getFrom() {
        return _keys.getRange().getFrom();
    }

    public double getTo() {
        return _keys.getRange().getTo();
    }

    public double getBy() {
        return _keys.getBy();
    }

    public int getIndexOfValue(Double value) {
        return _valueList.indexOf(value);
    }

    public List getKeyList() {
        return _keyList;
    }

    public boolean hasSameStructure(DiscreetValueMap sum) {
        return (_keys.equals(sum));
    }

    public void addValues(DiscreetValueMap valueMap) {
        for (int i = 0; i < valueMap.getKeys().length; ++i) {
            double key = valueMap.getKeys()[i];
            int index = getIndexOfKey(key);
            double sum = getValue(index) + valueMap.getValue(index);
            setValue(index, sum);
        }
    }

    /**
     * Given a key value finds which index this belongs to.
     * see DoubleSequence for implementation details
     *
     * @param key
     * @return
     */
    public int findIndexOfKey(double key, boolean wraparound) {
        return _keys.findIndexOfKey(key, wraparound);
    }

    public DiscreetValueMap normalise() {
        return createNormalised(this);
    }

    public DiscreetValueMap cumulative() {
        return createCumulative(this);
    }

    public DiscreetValueMap sum(DiscreetValueMap other) {
        List maps = new ArrayList();
        maps.add(this);
        maps.add(other);
        return createSum(maps);
    }

    public static DiscreetValueMap createCumulative(DiscreetValueMap pdistribution) {
        DiscreetValueMap cdist = pdistribution.copyStructure();

        double currentSum = 0;
        for (int i = 0; i < pdistribution.getValues().length; ++i) {
            currentSum += pdistribution.getValue(i);
            cdist.setValue(i, currentSum);
        }
        return cdist;
    }

    /**
     * Takes a distribution of values and "normalises" them - i.e. calculates each as a proportion of the total number
     * This gives you a probability density function where everything sums to 1
     *
     * @return
     */
    public static DiscreetValueMap createNormalised(DiscreetValueMap pdistribution) {
        DiscreetValueMap normalised = pdistribution.copyStructure();
        double sum = SummaryStatistics.calculateSum(pdistribution.getValues());
        for (int i = 0; i < pdistribution.getSize(); ++i) {
            normalised.setValue(i, (pdistribution.getValue(i) / sum));
        }
        return normalised;
    }

    public static DiscreetValueMap createSum(List valueMaps) {
        DiscreetValueMap t = (DiscreetValueMap)valueMaps.get(0);
        DiscreetValueMap sum = t.copyStructure();
        for (Iterator itr = valueMaps.iterator(); itr.hasNext();) {
            DiscreetValueMap valueMap = (DiscreetValueMap)itr.next();
            sum.addValues(valueMap);
        }
        return createNormalised(sum);
    }

    public double sumOfValues() {
        return SummaryStatistics.calculateSum(_values);
    }

    public double maxOfValues() {
        return DoubleMath.maxOf(_values);
    }

    public void writeToLog() {
        log.info("Discreet Value Map:\n");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < _keys.getSequence().length; ++i) {
            sb.append(_keys.getSequence()[i]);
            sb.append(", ");
            sb.append(_values[i]);

            if (i < _keys.getSequence().length - 1) {
                sb.append("\n ");
            }
        }


        log.info(sb.toString());
    }

    public double selectFromCumulativeProbability(double rnd) {

        return getKey(selectIndexFromCumulativeProbability(rnd));
    }

    public int selectIndexFromCumulativeProbability(double rnd) {
        int i = 0;
        boolean found = false;
        double precision = _keys.getPrecision();
        while (!found) {
//            if (DoubleMath.precisionGreaterThanEqual(_cdistribution.getValue(i), rnd, precision)) {
            if (_values[i] >= rnd) {
                found = true;
            } else if (i >= _values.length) {
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");

    private static final Logger log = Logger.getLogger(DiscreetValueMap.class);

    private DoubleSequence _keys;
    private double[] _values;
    private List _keyList;
    private List _valueList;
}
