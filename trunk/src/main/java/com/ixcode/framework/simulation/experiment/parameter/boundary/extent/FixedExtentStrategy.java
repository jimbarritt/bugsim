/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;

/**
 * Description : Represents a fixed boundary - simply has a parameter for the bounds and the shape
 */
public class FixedExtentStrategy extends ExtentStrategyBase {

    public static StrategyDefinitionParameter createDefaultStrategyParameter() {
        return createFixedExtentStrategyP(RectangularBoundaryStrategy.createRectangularBoundaryS(new CartesianDimensions(100)));
    }

    /**
     * Required for being a ParameterisedStrategy
     */
    public FixedExtentStrategy() {
    }


    public FixedExtentStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public static StrategyDefinitionParameter createFixedExtentStrategyP(StrategyDefinitionParameter boundaryShapeS) {
        return ExtentStrategyFactory.createBoundaryS(S_FIXED_EXTENT, FixedExtentStrategy.class, boundaryShapeS);
    }


    public static final String S_FIXED_EXTENT = "fixedExtentStrategy";
}
