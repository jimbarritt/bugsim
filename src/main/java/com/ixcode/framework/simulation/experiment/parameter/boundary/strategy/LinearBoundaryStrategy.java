/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;


/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LinearBoundaryStrategy extends BoundaryStrategyBase {

    public static StrategyDefinitionParameter createLinearBoundaryShapeS(RectangularCoordinate centre, double length, double rotation)  {
        throw new IllegalStateException("Method not implemented yet!!");
        // Eventually will be a factory method that works out what the start and end locations are based on the parameters you passed in.
        // then calls the other factory method.
    }
    public static StrategyDefinitionParameter createLinearBoundaryShapeS(RectangularCoordinate startLocation, RectangularCoordinate endLocation)  {
//        StrategyDefinitionParameter s = createBoundaryS(S_LINEAR, BoundaryShape.LINEAR);
//        s.addParameter(new Parameter(P_END_LOCATION, endLocation));
//        return s;
        throw new IllegalStateException("Method not implemented yet!!");
    }


    public BoundaryShape getBoundaryShape() {
        return  BoundaryShape.LINEAR;
    }

    public LinearBoundaryStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public RectangularCoordinate getStartLocation() {
        return super.getLocation();
    }

    public void setStartLocation(RectangularCoordinate startLocation) {
        super.setLocation(startLocation);
    }

    public RectangularCoordinate getEndLocation() {
        return (RectangularCoordinate)super.getParameter(P_END_LOCATION).getValue();
    }

    public void setEndLocation(RectangularCoordinate startLocation) {
        super.setParameterValue(P_END_LOCATION, startLocation);
    }


    public static final String S_LINEAR = "linearBoundaryStrategy";
    public  static final String P_END_LOCATION = "endLocation";
}
