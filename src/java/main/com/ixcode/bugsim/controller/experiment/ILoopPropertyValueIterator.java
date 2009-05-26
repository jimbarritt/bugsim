/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 * @deprecated
 */
public interface ILoopPropertyValueIterator {

    Object getStartValue();

    Object next(Object currentValue);

    boolean hasNext(Object currentValue);

    String getPropertyName();

    
}
