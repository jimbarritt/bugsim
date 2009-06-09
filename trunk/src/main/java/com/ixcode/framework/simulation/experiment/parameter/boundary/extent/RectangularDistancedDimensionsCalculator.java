/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : See Appendix on Simulation parameters for nice picture.
 */
public class RectangularDistancedDimensionsCalculator extends DerivedParameterCalculationBase {

    /**
     * Have to pass in the Parameter containing the strategy or else we don't know what name the strategy will have when we come to
     * look for it later...
     * @param innerBoundaryP
     * @param distanceP
     * @return
     */
    public static List createSourceParameters(Parameter innerBoundaryP, Parameter distanceP) {
        List params = new ArrayList();
        params.add(innerBoundaryP);
        params.add(distanceP);
        return params;
    }
    private static final int I_INNER_BOUNDARY = 0;
        private static final int I_DISTANCE = 1;


    public static final RectangularDistancedDimensionsCalculator INSTANCE = new RectangularDistancedDimensionsCalculator();

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        Parameter innerBoundaryP = forwardingMap.getParameter(I_INNER_BOUNDARY);
        List forwardingParams = getForwardingParameters(innerBoundaryP, forwardingMap.getParentParameterMap());

        forwardingMap.addUpdateListener(I_INNER_BOUNDARY);

        forwardingMap.addForward(I_DISTANCE);

        for (Iterator itr = forwardingParams.iterator(); itr.hasNext();) {
            forwardingMap.addForward((Parameter)itr.next());
        }

    }

    private List getForwardingParameters(Parameter innerBoundaryP, ParameterMap paramMap) {
        StrategyDefinitionParameter innerBoundaryS = innerBoundaryP.getStrategyDefinitionValue();
        BoundaryStrategyBase innerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(innerBoundaryS, paramMap, false);

        List params = new ArrayList();
        if (RectangularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)innerBoundary;
            params.add(rbs.getDimensionsP());
        } else if (CircularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            CircularBoundaryStrategy cbs = (CircularBoundaryStrategy)innerBoundary;
            params.add(cbs.getRadiusP());
        } else {
            throw new IllegalStateException("Cannot handle Boundary: " + innerBoundary.getClass().getName());
        }
        return params;
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        Parameter innerBoundaryP = sourceParams.getParameter(I_INNER_BOUNDARY);
        StrategyDefinitionParameter innerBoundaryS = innerBoundaryP.getStrategyDefinitionValue();
        Parameter distanceP = (Parameter)sourceParams.getParameter(I_DISTANCE);

        BoundaryStrategyBase innerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(innerBoundaryS, sourceParams.getParentParameterMap(), false);
        double distance = distanceP.getDoubleValue();

        return calculateOuterDimension(innerBoundary, distance);
    }

    public CartesianDimensions calculateOuterDimension(BoundaryStrategyBase innerBoundary, double d) {
        double width;
        double height;

        if (RectangularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            double dd = d * 2;
            RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)innerBoundary;
            CartesianDimensions innerDimensions = rbs.getDimensions();
            width = innerDimensions.getDoubleWidth() + dd;
            height = innerDimensions.getDoubleHeight() + dd;
        } else if (CircularBoundaryStrategy.class.isAssignableFrom(innerBoundary.getClass())) {
            CircularBoundaryStrategy cbs = (CircularBoundaryStrategy)innerBoundary;
            double r = cbs.getRadius();
            double derivedR = (r + d) * 2d;
            width = derivedR;
            height = derivedR;
        } else {
            throw new IllegalStateException("Cannot handle Boundary: " + innerBoundary.getClass().getName());
        }


        return new CartesianDimensions(width, height);
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
