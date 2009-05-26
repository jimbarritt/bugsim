/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.CircularDistancedRadiusCalculator;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryFactory;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.List;


/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo could potentially implement an elliptical boundary which would have a radius and something else - or actually a width anda height or a CartesianBounds
 */
public class CircularBoundaryStrategy extends BoundaryStrategyBase {

    public static StrategyDefinitionParameter createCircularBoundaryS(RectangularCoordinate location, double radius, ShapeLocationType locationType) {
        Parameter locationP = new Parameter(P_LOCATION, location);
        Parameter locationTypeP = new Parameter(P_LOCATION_TYPE, locationType);
        Parameter radiusP = new Parameter(P_RADIUS, radius);

        return createCircularBoundaryS(locationP, locationTypeP, radiusP);

    }

    private static StrategyDefinitionParameter createCircularBoundaryS(Parameter locationP, Parameter locationTypeP, Parameter radiusP) {
        List boundsParams= CircularBoundaryBoundsCalculation.createSourceParameters(locationP, locationTypeP, radiusP);
        DerivedParameter boundsDP = new DerivedParameter(P_BOUNDS, boundsParams, new CircularBoundaryBoundsCalculation());

        Class boundaryClass = BoundaryFactory.getBoundaryClassForShape(BoundaryShape.CIRCULAR);
        StrategyDefinitionParameter s = new StrategyDefinitionParameter(S_CIRCULAR, boundaryClass.getName());

        s.addParameter(boundsDP);
        s.addParameter(locationP);
        s.addParameter(locationTypeP);
        s.addParameter(radiusP);
        return s;
    }

    public static StrategyDefinitionParameter createDistancedDimensionBoundaryS(Parameter sourceBoundaryP, Parameter distanceP, ShapeLocationType locationType) {
        List sourceParams = CircularDistancedRadiusCalculator.createSourceParameters(sourceBoundaryP, distanceP);
        return createCalculatedDimensionBoundaryS(CircularDistancedRadiusCalculator.INSTANCE, sourceParams, locationType);
    }

    /**
     * This method creates a Rectangular Boundary Strategy that has it's Dimension derived from a calculation - you must pass
     * in th calculation which allows different kinds of calculation to be used - as long as they return a CartesianDimension its all good.
     * As is standard , the bounds of the strategy is derived from the location, type and the dimension.
     *
     * @param dimensionCalc
     * @param radiusSourceParams
     * @return
     */
    public static StrategyDefinitionParameter createCalculatedDimensionBoundaryS(IDerivedParameterCalculation dimensionCalc, List radiusSourceParams, ShapeLocationType locationType) {
        Parameter locationP = new Parameter(P_LOCATION, new RectangularCoordinate(0, 0));
        Parameter locationTypeP = new Parameter(P_LOCATION_TYPE, locationType);
        DerivedParameter radiusDP = new DerivedParameter(P_RADIUS, radiusSourceParams, dimensionCalc);

        return createCircularBoundaryS(locationP, locationTypeP, radiusDP);

    }
    public static StrategyDefinitionParameter createDefaultStrategyParameter() {
        return createCircularBoundaryS(new RectangularCoordinate(50, 50), 50, ShapeLocationType.CENTRE);
    }


    public CircularBoundaryStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }


    /**
     * Not correct if the Location type is not cENTRE!
     * @return
     */
    public RectangularCoordinate getCenter() {
        throw new IllegalStateException("Not implemented");
//        return super.getLocation();
    }

    public void setCenter(RectangularCoordinate location) {
        super.setLocation(location);                         

    }

    public BoundaryShape getBoundaryShape() {
        return BoundaryShape.CIRCULAR;
    }

    public double getRadius() {
        return getParameter(P_RADIUS).getDoubleValue();
    }

    public void setRadius(double radius) {
        super.setParameterValue(P_RADIUS,radius);
    }

    public void setCenterP(Parameter locationP) {
        super.setParameter(locationP);
    }

    public void setRadiusP(Parameter radiusP) {
        super.setParameter(radiusP);
    }

    public Parameter getRadiusP() {
        return super.getParameter(P_RADIUS);
    }

    public boolean isRadiusDerived() {
        return getRadiusP().isDerived();
    }

    public static final String S_CIRCULAR = "circularBoundaryStrategy";
    public static final String P_RADIUS = "radius";
}
