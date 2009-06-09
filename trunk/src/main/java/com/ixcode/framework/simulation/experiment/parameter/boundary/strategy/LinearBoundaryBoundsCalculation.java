/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.DerivedParameterCalculationBase;
import com.ixcode.framework.parameter.model.ISourceParameterForwardingMap;
import com.ixcode.framework.parameter.model.ISourceParameterMap;

/**
 * Description : Knows how to calculate the bounds of a circle...
 * Created     : Jan 26, 2007 @ 10:09:53 AM by jim
 */
public class LinearBoundaryBoundsCalculation extends DerivedParameterCalculationBase {

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        forwardingMap.addForward(BoundaryStrategyBase.P_LOCATION);
        forwardingMap.addForward(BoundaryStrategyBase.P_LOCATION_TYPE);
        forwardingMap.addForward(LinearBoundaryStrategy.P_END_LOCATION);
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        RectangularCoordinate location = (RectangularCoordinate)sourceParams.getParameter(BoundaryStrategyBase.P_LOCATION).getValue();
        ShapeLocationType locationType = (ShapeLocationType)sourceParams.getParameter(BoundaryStrategyBase.P_LOCATION_TYPE).getValue();

        RectangularCoordinate endLocation = (RectangularCoordinate)sourceParams.getParameter(LinearBoundaryStrategy.P_END_LOCATION).getValue();


        CartesianBounds derivedBounds = null;
        if (locationType == ShapeLocationType.CENTRE) {
            derivedBounds = calculateFromCentre(location, endLocation);
        } else if (locationType == ShapeLocationType.BOTTOM_LEFT) {
            derivedBounds = calculateFromCorner(location, endLocation);
        } else if (locationType == ShapeLocationType.BOTTOM_RIGHT) {
            derivedBounds = calculateFromCorner(location, endLocation);
        } else if (locationType == ShapeLocationType.TOP_LEFT) {
            derivedBounds = calculateFromCorner(location, endLocation);
        } else if (locationType == ShapeLocationType.TOP_RIGHT) {
            derivedBounds = calculateFromCorner(location, endLocation);
        } else {
            throw new IllegalStateException("Cannot calculate Derived bounds for rectangle for type: " + locationType);
        }


        return derivedBounds;
    }

    private CartesianBounds calculateFromCorner(RectangularCoordinate location, RectangularCoordinate endLocation) {
        double x = Math.min(location.getDoubleX(), endLocation.getDoubleX());
        double y = Math.min(location.getDoubleY(), endLocation.getDoubleY());

        double w = Math.max(location.getDoubleX(), endLocation.getDoubleX()) - Math.min(location.getDoubleX(), endLocation.getDoubleX());
        double h = Math.max(location.getDoubleY(), endLocation.getDoubleY()) - Math.min(location.getDoubleY(), endLocation.getDoubleY());


        return new CartesianBounds(x, y, w, h);
    }

    private CartesianBounds calculateFromCentre(RectangularCoordinate location, RectangularCoordinate endLocation) {
        CartesianBounds b = calculateFromCorner(location, endLocation);
        return new CartesianBounds(b.getDoubleX(), b.getDoubleY(), b.getDoubleWidth()*2, b.getDoubleHeight()*2);
    }
}
