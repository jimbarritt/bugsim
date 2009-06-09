/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : Calculates the centre point of a Boundary...
 * Created     : Jan 29, 2007 @ 11:18:31 PM by jim
 */
public class CentredBoundaryLocationDerivedCalculation implements IDerivedParameterCalculation {


    public static List createSourceParameters(Parameter outerBoundaryP, Parameter innerBoundaryP) {
        List params = new ArrayList();
        params.add(outerBoundaryP);
        params.add(innerBoundaryP);
        return params;
    }


    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        Parameter outerBoundaryP = forwardingMap.getFirstParameter();

        forwardingMap.addUpdateListener(0);

        StrategyDefinitionParameter outerBoundaryS = outerBoundaryP.getStrategyDefinitionValue();
        BoundaryStrategyBase outerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(outerBoundaryS, forwardingMap.getParentParameterMap(), false);
        Parameter outerBoundsP = outerBoundary.getBoundsP();


        forwardingMap.addForward(outerBoundsP);

        Parameter innerBoundaryP = forwardingMap.getParameter(1);
        BoundaryStrategyBase innerBoundary = getBoundaryStrategy(innerBoundaryP, forwardingMap);

        Parameter sizeP = null;
        if (innerBoundary instanceof RectangularBoundaryStrategy) {
            sizeP = ((RectangularBoundaryStrategy)innerBoundary).getDimensionsP();
        } else if (innerBoundary instanceof CircularBoundaryStrategy) {
            sizeP = ((CircularBoundaryStrategy)innerBoundary).getRadiusP();
        } else {
            throw new IllegalArgumentException("Unkonwn boundary strategy: " + innerBoundary);
        }

        // need to add this incase the outer boundard doesnt derive from the inner boundary...
        forwardingMap.addForward(sizeP);

    }

    /**
     * Cant use the BOUNDS parameter directly as it can get circular...
     * @param sourceParams
     * @return
     */
    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        Parameter outerBoundaryP = sourceParams.getFirstParameter();
        Parameter innerBoundaryP = sourceParams.getParameter(1);

        BoundaryStrategyBase outerBoundary = getBoundaryStrategy(outerBoundaryP, sourceParams);
        BoundaryStrategyBase innerBoundary = getBoundaryStrategy(innerBoundaryP, sourceParams);


        RectangularCoordinate centre = calculateCentre(outerBoundary);

        RectangularCoordinate location = centre;

        if (innerBoundary instanceof RectangularBoundaryStrategy) {
            CartesianDimensions size = ((RectangularBoundaryStrategy)innerBoundary).getDimensions();
            location = calculateRectangularLocation(centre, size, innerBoundary.getLocationType());
        }

        return location;

    }

    private RectangularCoordinate calculateCentre(BoundaryStrategyBase outerBoundary) {
        BoundaryShape shape = outerBoundary.getBoundaryShape();

        RectangularCoordinate centre = null;
        if (shape.isCircular()) {
            CircularBoundaryStrategy cbs = (CircularBoundaryStrategy)outerBoundary;
            if (cbs.getLocationType() == ShapeLocationType.CENTRE) {
                centre = cbs.getLocation();
            } else if (cbs.getLocationType() == ShapeLocationType.BOTTOM_LEFT) {
                centre = new RectangularCoordinate(cbs.getRadius(), cbs.getRadius());
            }  else {
                throw new IllegalStateException("Cannot handle ShapeLocationType: " + cbs.getLocationType());
            }
        } else if (shape.isRectangular())  {
             RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)outerBoundary;
            if (rbs.getLocationType() == ShapeLocationType.CENTRE) {
                centre = rbs.getLocation();
            } else if (rbs.getLocationType() == ShapeLocationType.BOTTOM_LEFT) {
                centre = new RectangularCoordinate(rbs.getDimensions().getDoubleWidth(), rbs.getDimensions().getDoubleHeight());                 
            }  else {
                throw new IllegalStateException("Cannot handle ShapeLocationType: " + rbs.getLocationType());
            }
        }  else {
            throw new IllegalStateException("Cannot handle boundary shape : " + shape);
        }
        return centre;

    }

    private RectangularCoordinate calculateRectangularLocation(RectangularCoordinate centre, CartesianDimensions size, ShapeLocationType locationType) {
        double x = centre.getDoubleX();
        double y = centre.getDoubleY();
        double halfWidth = size.getDoubleWidth() / 2;
        double halfHeight = size.getDoubleHeight() / 2;
        if (locationType == ShapeLocationType.CENTRE) {
            // do nothing
        } else if (locationType == ShapeLocationType.BOTTOM_LEFT) {
            x  -= halfWidth;
            y -=  halfHeight;
        } else if (locationType == ShapeLocationType.BOTTOM_RIGHT) {
            x  += halfWidth;
            y -=  halfHeight;
        } else if (locationType == ShapeLocationType.TOP_LEFT) {
            x  -= halfWidth;
            y +=  halfHeight;
        } else if (locationType == ShapeLocationType.TOP_RIGHT) {
            x  += halfWidth;
            y +=  halfHeight;
        } else {
            throw new IllegalStateException("Cannot calculate Derived bounds for rectangle for type: " + locationType);
        }
        return new RectangularCoordinate(x, y);
    }

    private BoundaryStrategyBase getBoundaryStrategy(Parameter outerBoundaryP, ISourceParameterMap sourceParams) {
        StrategyDefinitionParameter boundaryS = outerBoundaryP.getStrategyDefinitionValue();
        BoundaryStrategyBase boundaryStrategy = BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, sourceParams.getParentParameterMap(), false);
        return boundaryStrategy;
    }
     private BoundaryStrategyBase getBoundaryStrategy(Parameter outerBoundaryP, ISourceParameterForwardingMap forwardingMap) {
        StrategyDefinitionParameter boundaryS = outerBoundaryP.getStrategyDefinitionValue();
        BoundaryStrategyBase boundaryStrategy = BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, forwardingMap.getParentParameterMap(), false);
        return boundaryStrategy;
    }

    private static final Logger log = Logger.getLogger(CentredBoundaryLocationDerivedCalculation.class);
}
