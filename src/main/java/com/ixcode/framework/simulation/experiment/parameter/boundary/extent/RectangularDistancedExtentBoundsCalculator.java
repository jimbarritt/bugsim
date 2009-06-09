/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.ArrayList;
import java.util.List;

/**
 * @toso this is all wrong!! should be passing in th eparent parameter of the strategy!!
 * Description : See Appendix on Simulation parameters for nice picture.
 */
public class RectangularDistancedExtentBoundsCalculator implements IDerivedParameterCalculation {
    public static final RectangularDistancedExtentBoundsCalculator INSTANCE = new RectangularDistancedExtentBoundsCalculator();

    public static List createSourceParameters(StrategyDefinitionParameter innerBoundaryS, Parameter distanceP) {
        List params = new ArrayList();
        params.add(innerBoundaryS);
        params.add(distanceP);
        return params;
    }

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
// This should contain the P_INNER_BOUNDARY, not the Strategy!!!
        StrategyDefinitionParameter innerBoundaryS = (StrategyDefinitionParameter)forwardingMap.getParameter(DistancedExtentStrategy.P_INNER_BOUNDARY);
        Parameter sourceBoundsP = getSourceBoundsP(innerBoundaryS, forwardingMap.getParentParameterMap());

        forwardingMap.addUpdateListenerToParentOf(innerBoundaryS);

        forwardingMap.addForward(DistancedExtentStrategy.P_DISTANCE);
        forwardingMap.addForward(sourceBoundsP);

    }


    private Parameter getSourceBoundsP(StrategyDefinitionParameter innerBoundaryS, ParameterMap params) {
        BoundaryStrategyBase innerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(innerBoundaryS, params, false);
        Parameter sourceBoundsP = null;
        if (RectangularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)innerBoundary;
            sourceBoundsP = rbs.getBoundsP();
        } else if (CircularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            CircularBoundaryStrategy cbs = (CircularBoundaryStrategy)innerBoundary;
            sourceBoundsP = cbs.getRadiusP();
        } else {
            throw new IllegalStateException("Cannot handle Boundary: " + innerBoundary.getClass().getName());
        }
        return sourceBoundsP;
    }


    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {

        StrategyDefinitionParameter innerBoundaryS = (StrategyDefinitionParameter)sourceParams.getParameter(DistancedExtentStrategy.P_INNER_BOUNDARY);
        Parameter distanceP = sourceParams.getParameter(DistancedExtentStrategy.P_DISTANCE);

        BoundaryStrategyBase innerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(innerBoundaryS, sourceParams.getParentParameterMap(), false);

        double distance = distanceP.getDoubleValue();

        return calculateOuterBounds(innerBoundary, distance);
    }

    public CartesianBounds calculateOuterBounds(BoundaryStrategyBase innerBoundary, double d) {
        double width;
        double height;

        if (RectangularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            double dd = d * 2;
            RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)innerBoundary;
            CartesianBounds innerBounds = rbs.getBounds();
            width = innerBounds.getDoubleWidth() + dd;
            height = innerBounds.getDoubleHeight() + dd;
        } else if (CircularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            CircularBoundaryStrategy cbs = (CircularBoundaryStrategy)innerBoundary;
            double r = cbs.getRadius();
            double derivedR = (r + d) * 2d;
            width = derivedR;
            height = derivedR;
        } else {
            throw new IllegalStateException("Cannot handle Boundary: " + innerBoundary.getClass().getName());
        }


        return new CartesianBounds(0, 0, width, height);
    }

    /**
     * @param shape
     * @param sourceBounds
     * @param d
     * @param sourceShape
     * @return
     * @deprecated You should use the other method and use the strategies properly.
     */
    public CartesianBounds calculateOuterBounds(BoundaryShape shape, CartesianBounds sourceBounds, double d, BoundaryShape sourceShape) {
        double width;
        double height;
        if (shape == BoundaryShape.RECTANGULAR) {
            double dd = d * 2;
            width = sourceBounds.getDoubleWidth() + dd;
            height = sourceBounds.getDoubleHeight() + dd;
        } else if (shape == BoundaryShape.CIRCULAR) {
            double r = (sourceShape == BoundaryShape.RECTANGULAR) ? sourceBounds.getRadiusOfEnclosingCircle() : sourceBounds.getRadiusOfInnerCircle();
            double derivedR = (r + d) * 2d;
            width = derivedR;
            height = derivedR;
        } else {
            throw new IllegalStateException("Unkown Border Shape: " + shape);
        }

        return new CartesianBounds(0, 0, width, height);
    }


    private static final String P_PARAMETER_MAP = "parameterMap";
}
