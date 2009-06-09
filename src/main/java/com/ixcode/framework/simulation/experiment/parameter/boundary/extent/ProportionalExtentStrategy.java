/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;


/**
 * Description : Calculates the size of the extent as a proportion of the source boundary (so 0.5 times the size, or 3 * size)
 */
public class ProportionalExtentStrategy extends ExtentStrategyBase {

    /**
     * Required for being a ParameterisedStrategy
     */
    public ProportionalExtentStrategy() {
    }


    public ProportionalExtentStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public static final String S_PROPORTIONAL_EXTENT = "proportionalExtent";
}
