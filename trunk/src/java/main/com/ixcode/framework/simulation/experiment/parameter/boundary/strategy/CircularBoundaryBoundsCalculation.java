/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.DerivedParameterCalculationBase;
import com.ixcode.framework.parameter.model.ISourceParameterForwardingMap;
import com.ixcode.framework.parameter.model.ISourceParameterMap;
import com.ixcode.framework.parameter.model.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : Knows how to calculate the bounds of a circle...
 * Created     : Jan 26, 2007 @ 10:09:53 AM by jim
 */
public class CircularBoundaryBoundsCalculation extends DerivedParameterCalculationBase {

    public static List createSourceParameters(Parameter locationP, Parameter locationTypeP, Parameter radiusP) {
        List params = new ArrayList();
        params.add(locationP);
        params.add(locationTypeP);
        params.add(radiusP);
        return params;
    }
    



    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        forwardingMap.addForward(CircularBoundaryStrategy.P_RADIUS);
        forwardingMap.addForward(CircularBoundaryStrategy.P_LOCATION);
        forwardingMap.addForward(CircularBoundaryStrategy.P_LOCATION_TYPE);
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {

        double radius = sourceParams.getParameter(CircularBoundaryStrategy.P_RADIUS).getDoubleValue();
        RectangularCoordinate location = (RectangularCoordinate)sourceParams.getParameter(CircularBoundaryStrategy.P_LOCATION).getValue();
        ShapeLocationType locationType = (ShapeLocationType)sourceParams.getParameter(CircularBoundaryStrategy.P_LOCATION_TYPE).getValue();

        RectangularCoordinate  boundsLocation = location;
        if (locationType == ShapeLocationType.CENTRE) {
            boundsLocation = calculateFromCentre(radius, location);
        } else if (locationType == ShapeLocationType.BOTTOM_LEFT) {
            // do nothing
        }  else if (locationType == ShapeLocationType.TOP_LEFT) {
            boundsLocation = calculateFromTopLeft(radius, location);
        } else if (locationType == ShapeLocationType.BOTTOM_RIGHT) {
            boundsLocation = calculateFromBottomright(radius, location);
        } else if (locationType == ShapeLocationType.TOP_RIGHT) {
            boundsLocation = calculateFromTopRight(radius, location);
        }else {
            throw new IllegalStateException("Cannot calculate Derived bounds for circle unless location is in centre!");
        }



        return new CartesianBounds(boundsLocation, new CartesianDimensions(radius*2));
    }

    private RectangularCoordinate calculateFromTopLeft(double radius, RectangularCoordinate location) {
        double diameter = radius*2;
        return new RectangularCoordinate(location.getDoubleX(), location.getDoubleY()-diameter);
    }

    private RectangularCoordinate calculateFromTopRight(double radius, RectangularCoordinate location) {
        double diameter = radius*2;
        return new RectangularCoordinate(location.getDoubleX()-diameter, location.getDoubleY()-diameter);
    }

    private RectangularCoordinate calculateFromBottomright(double radius, RectangularCoordinate location) {
        double diameter = radius*2;
        return new RectangularCoordinate(location.getDoubleX()-diameter, location.getDoubleY());
    }

    private RectangularCoordinate calculateFromCentre(double radius, RectangularCoordinate location) {
        return new RectangularCoordinate(location.getDoubleX() - radius, location.getDoubleY()-radius);
    }


}
