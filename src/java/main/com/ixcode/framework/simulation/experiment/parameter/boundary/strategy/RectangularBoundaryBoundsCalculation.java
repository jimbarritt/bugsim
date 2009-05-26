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
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : Knows how to calculate the bounds of a circle...
 * Created     : Jan 26, 2007 @ 10:09:53 AM by jim
 */
public class RectangularBoundaryBoundsCalculation extends DerivedParameterCalculationBase {


    public static List createSourceParameters(StrategyDefinitionParameter boundaryS) {
        List sourceParams = new ArrayList();

        sourceParams.add(boundaryS);

        return sourceParams;
    }

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        StrategyDefinitionParameter boundaryS = (StrategyDefinitionParameter)forwardingMap.getFirstParameter();

        RectangularBoundaryStrategy boundary = (RectangularBoundaryStrategy)BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, forwardingMap.getParentParameterMap(), false);


        forwardingMap.addUpdateListenerFromFirstParameterReference();
        //Need to listen to them all for updates incase they get disconnected ... OR have the boundary strategy fire a parameter changed event...


        forwardingMap.addForward(boundary.getLocationP());
        forwardingMap.addForward(boundary.getLocationTypeP());
        forwardingMap.addForward(boundary.getDimensionsP());
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        StrategyDefinitionParameter boundaryS = (StrategyDefinitionParameter)sourceParams.getFirstParameter();


        BoundaryStrategyBase boundaryBase = BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, sourceParams.getParentParameterMap(), false);
        if (!(boundaryBase instanceof RectangularBoundaryStrategy)) {
            throw new IllegalStateException("Somehow we are set up to calculate rectangualr bounds with a circular boundary!");
        }

        RectangularBoundaryStrategy boundary = (RectangularBoundaryStrategy)boundaryBase;
        
        RectangularCoordinate location = boundary.getLocation();
        ShapeLocationType locationType = boundary.getLocationType();
        CartesianDimensions size = boundary.getDimensions();


        RectangularCoordinate boundsLocation = location;
        if (locationType == ShapeLocationType.CENTRE) {
            boundsLocation = calculateFromCentre(size, location);
        } else if (locationType == ShapeLocationType.BOTTOM_LEFT) {
            // do nothing
        } else if (locationType == ShapeLocationType.BOTTOM_RIGHT) {
            boundsLocation = calculateFromBottomRight(size, location);
        } else if (locationType == ShapeLocationType.TOP_LEFT) {
            boundsLocation = calculateFromTopLeft(size, location);
        } else if (locationType == ShapeLocationType.TOP_RIGHT) {
            boundsLocation = calculateFromTopRight(size, location);
        } else {
            throw new IllegalStateException("Cannot calculate Derived bounds for rectangle for type: " + locationType);
        }


        return new CartesianBounds(boundsLocation, size);
    }

    private RectangularCoordinate calculateFromTopRight(CartesianDimensions size, RectangularCoordinate location) {
        return new RectangularCoordinate(location.getDoubleX() - size.getDoubleWidth(), location.getDoubleY() - size.getDoubleHeight());
    }

    private RectangularCoordinate calculateFromTopLeft(CartesianDimensions size, RectangularCoordinate location) {
        return new RectangularCoordinate(location.getDoubleX(), location.getDoubleY() - size.getDoubleHeight());
    }

    private RectangularCoordinate calculateFromBottomRight(CartesianDimensions size, RectangularCoordinate location) {
        return new RectangularCoordinate(location.getDoubleX() - size.getDoubleWidth(), location.getDoubleY());
    }

    private RectangularCoordinate calculateFromCentre(CartesianDimensions size, RectangularCoordinate location) {
        return new RectangularCoordinate(location.getDoubleX() - (size.getDoubleWidth() / 2), location.getDoubleY() - (size.getDoubleHeight() / 2));
    }
    private static final Logger log = Logger.getLogger(RectangularBoundaryBoundsCalculation.class);
}
