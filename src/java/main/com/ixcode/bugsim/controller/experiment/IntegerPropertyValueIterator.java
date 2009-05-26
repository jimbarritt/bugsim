/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 * Description : ${CLASS_DESCRIPTION}
 * @deprecated
 */
public class IntegerPropertyValueIterator extends PropertyValueIteratorBase {

    public IntegerPropertyValueIterator(String propertyName, Object start, Object max, Object increment) {
        super(propertyName, start, max, increment);

        _max = ((Integer)max).intValue();
        _increment = ((Integer)increment).intValue();
        System.out.println("Integer Property Value Iterator : " + _max + " : incr " + _increment);
    }

    public Object next(Object currentValue) {
        Integer current = (Integer)currentValue;

        int nextValue;

        nextValue = current.intValue() + _increment;

        return new Integer(nextValue);
    }

    public boolean hasNext(Object currentValue) {
        int  nextValue = ((Integer)currentValue).intValue() + _increment;
        boolean hasNext = false;
        if (nextValue < _max) {
            hasNext = true;
        }

        System.out.println("HasNext: " + hasNext + ", nextValue = " + nextValue + ", currentValue = " + currentValue);
        return hasNext;
    }



    private int _max;
    private int _increment;


}
