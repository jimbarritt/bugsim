/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Description : Calculates the centre point of a Boundary...
 * Created     : Jan 29, 2007 @ 11:18:31 PM by jim
 */
public class CentredLocationDerivedCalculation implements IDerivedParameterCalculation {


    public static List createSourceParameters(Parameter outerBoundaryP) {
        List params = new ArrayList();
        params.add(outerBoundaryP);
        return params;
    }


    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        Parameter outerBoundaryP = forwardingMap.getFirstParameter();

        forwardingMap.addUpdateListener(0);

        StrategyDefinitionParameter outerBoundaryS = outerBoundaryP.getStrategyDefinitionValue();
        BoundaryStrategyBase outerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(outerBoundaryS, forwardingMap.getParentParameterMap(), false);
        Parameter outerBoundsP = outerBoundary.getBoundsP();


        forwardingMap.addForward(outerBoundsP);



    }

    /**
     * Cant use the BOUNDS parameter directly as it can get circular...
     * @param sourceParams
     * @return
     */
    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        Parameter outerBoundaryP = sourceParams.getFirstParameter();


        BoundaryStrategyBase outerBoundary = getBoundaryStrategy(outerBoundaryP, sourceParams);


        return calculateCentre(outerBoundary);

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


    private BoundaryStrategyBase getBoundaryStrategy(Parameter outerBoundaryP, ISourceParameterMap sourceParams) {
        StrategyDefinitionParameter boundaryS = outerBoundaryP.getStrategyDefinitionValue();
        BoundaryStrategyBase boundaryStrategy = BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, sourceParams.getParentParameterMap(), false);
        return boundaryStrategy;
    }
    

    private static final Logger log = Logger.getLogger(CentredLocationDerivedCalculation.class);
}
