/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryBase;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class BoundaryStrategyBase extends StrategyDefinition {

    public BoundaryStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

    }

    public BoundaryBase createBoundary(Map initialisationObjects) {
        return (BoundaryBase)ParameterisedStrategyFactory.createParameterisedStrategy(super.getStrategyS(), super.getParameterMap(), initialisationObjects);
    }

    public RectangularCoordinate getLocation() {
        return (RectangularCoordinate)super.getParameter(P_LOCATION).getValue();
    }

    public void setLocation(RectangularCoordinate location) {
        super.setParameterValue(P_LOCATION, location);
    }

    public ShapeLocationType getLocationType() {
        return (ShapeLocationType)super.getParameter(P_LOCATION_TYPE).getValue();
    }

    public void setLocationType(ShapeLocationType locationType) {
        super.setParameterValue(P_LOCATION_TYPE, locationType);

    }

    public boolean isLocationDerived() {
        return (super.getParameter(P_LOCATION) instanceof DerivedParameter);
    }


    public CartesianBounds getBounds() {
        return (CartesianBounds)super.getParameter(P_BOUNDS).getValue();
    }

    public Parameter getLocationP() {
        return super.getParameter(P_LOCATION);
    }
    public void setLocationP(Parameter locationP) {
        super.setParameter(locationP);
    }
    public Parameter getLocationTypeP() {
            return super.getParameter(P_LOCATION_TYPE);
        }


    public Parameter getBoundsP() {
        return super.getParameter(P_BOUNDS) ;
    }

    public abstract BoundaryShape getBoundaryShape();

    

    private static final Logger log = Logger.getLogger(BoundaryStrategyBase.class);

    public static final String P_LOCATION = "location";
    public static final String P_LOCATION_TYPE = "locationType"; // Centre, TopLeft, TopRight, BottomLeft, BottomRight
    public static final String P_BOUNDS = "bounds"; // always derived from the sub class parameters
}
