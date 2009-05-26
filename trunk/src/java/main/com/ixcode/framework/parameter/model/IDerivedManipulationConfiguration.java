/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import java.util.List;


/**
 * The difference between this and a derived parameter is that this one can change the way it works out the derived value
 * with each iteration whereas a derived parameter ALLWAYS reflects the state of its source in the same way.
 *  
 */
public interface IDerivedManipulationConfiguration {
    void configureDerivedParameter(Parameter currentDerivedParameter, List currentSourceParameters);
}
