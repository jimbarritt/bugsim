/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Description : As constructing the various extents is quite complex, we have moved all the factory methods in here.
 * Created     : Jan 28, 2007 @ 11:42:53 AM by jim
 */
public class DistancedExtentStrategyFactory {

    private DistancedExtentStrategyFactory() {
    }

    public static StrategyDefinitionParameter createDefaultStrategyParameter() {
        Parameter innerBoundaryP = createFixedInnerBoundary(100);
        Parameter distanceP = new Parameter(DistancedExtentStrategy.P_DISTANCE, 10d);


        StrategyDefinitionParameter outerBoundaryS = createOuterBoundary(BoundaryShape.RECTANGULAR, innerBoundaryP, distanceP, ShapeLocationType.BOTTOM_LEFT);

        return createDistancedExtentS(outerBoundaryS, innerBoundaryP, distanceP);
    }

    /**
     * Creates a distanced boundary with a inner rectangle sourced from somewhere else - you need to supply an entire boundary strategy...
     *
     * @return
     */
    public static StrategyDefinitionParameter createDistancedExtentS(BoundaryShape outerShape,Parameter sourceBoundaryP, double distance, ShapeLocationType locationType) {
        Parameter innerBoundaryP = createDerivedInnerBoundary(sourceBoundaryP);
        Parameter distanceP = new Parameter(DistancedExtentStrategy.P_DISTANCE, distance);

        StrategyDefinitionParameter outerBoundaryS = createOuterBoundary(outerShape, innerBoundaryP, distanceP, locationType);

        return createDistancedExtentS(outerBoundaryS, innerBoundaryP, distanceP);
    }

    private static StrategyDefinitionParameter createDistancedExtentS(StrategyDefinitionParameter outerBoundaryS, Parameter innerBoundaryP, Parameter distanceP) {
        StrategyDefinitionParameter distancedS = ExtentStrategyFactory.createBoundaryS(DistancedExtentStrategy.S_DISTANCED_EXTENT, DistancedExtentStrategy.class, outerBoundaryS);

        distancedS.addParameter(innerBoundaryP);
        distancedS.addParameter(distanceP);
        return distancedS;
    }

    private static StrategyDefinitionParameter createOuterBoundary(BoundaryShape shape, Parameter innerBoundaryP, Parameter distanceP, ShapeLocationType  locationType) {
        StrategyDefinitionParameter outerBoundaryS = null;
        if (shape.isRectangular()) {
            outerBoundaryS = RectangularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP);
        } else if (shape.isCircular()) {
            outerBoundaryS = CircularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP, locationType);
        } else {
            throw new IllegalStateException("Cannot create distanced boundary of shape: " + shape);
        }


        return  outerBoundaryS;
    }

    private static Parameter createFixedInnerBoundary(double dimension) {
        StrategyDefinitionParameter innerBoundaryS = RectangularBoundaryStrategy.createRectangularBoundaryS(new CartesianDimensions(dimension));
        return new Parameter(DistancedExtentStrategy.P_INNER_BOUNDARY, innerBoundaryS);
    }

    private static Parameter createDerivedInnerBoundary(Parameter sourceBoundaryP) {
        IDerivedParameterCalculation innerBoundaryCalc = new DerivedStrategyCalculation();
        List innerSourceParams = DerivedStrategyCalculation.createSourceParameters(sourceBoundaryP);
        return new DerivedParameter(DistancedExtentStrategy.P_INNER_BOUNDARY, innerSourceParams, innerBoundaryCalc);
    }

    private static final Logger log = Logger.getLogger(DistancedExtentStrategyFactory.class);
}
