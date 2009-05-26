/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

public interface IDerivedParameterCalculation {
    Object calculateDerivedValue(ISourceParameterMap sourceParams);


    /**
     * Note we dont jsut return a list of names because the source params can contain top level objects like a strategy parameter.
     * @param forwardingMap
     * @return the source parameters which are important to this calculation so we know which ones to attatch forwarding to.
     */
    void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap);
}
