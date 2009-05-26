/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.RectangularDistancedDimensionsCalculator;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryFactory;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class RectangularBoundaryStrategy extends BoundaryStrategyBase {

    public static StrategyDefinitionParameter createDefaultStrategyParameter() {
        return createRectangularBoundaryS(new CartesianDimensions(100));
    }


    public static StrategyDefinitionParameter createRectangularBoundaryS(CartesianDimensions size) {
        Parameter locationP = new Parameter(P_LOCATION, new RectangularCoordinate(0, 0));
        Parameter locationTypeP = new Parameter(P_LOCATION_TYPE, ShapeLocationType.BOTTOM_LEFT);
        Parameter dimensionP = new Parameter(P_DIMENSIONS, size);

        return createRectangularBoundaryS(locationP, locationTypeP, dimensionP);
    }

    public static StrategyDefinitionParameter createDistancedDimensionBoundaryS(Parameter sourceBoundaryP, Parameter distanceP) {
        List sourceParams = RectangularDistancedDimensionsCalculator.createSourceParameters(sourceBoundaryP, distanceP);
        return RectangularBoundaryStrategy.createCalculatedDimensionBoundaryS(RectangularDistancedDimensionsCalculator.INSTANCE, sourceParams);
    }

    /**
     * This method creates a Rectangular Boundary Strategy that has it's Dimension derived from a calculation - you must pass
     * in th calculation which allows different kinds of calculation to be used - as long as they return a CartesianDimension its all good.
     * As is standard , the bounds of the strategy is derived from the location, type and the dimension.
     *
     * @param dimensionCalc
     * @param dimensionSourceParams
     * @return
     */
    public static StrategyDefinitionParameter createCalculatedDimensionBoundaryS(IDerivedParameterCalculation dimensionCalc, List dimensionSourceParams) {
        Parameter locationP = new Parameter(P_LOCATION, new RectangularCoordinate(0, 0));
        Parameter locationTypeP = new Parameter(P_LOCATION_TYPE, ShapeLocationType.BOTTOM_LEFT);
        DerivedParameter dimensionDP = new DerivedParameter(P_DIMENSIONS, dimensionSourceParams, dimensionCalc);

        return createRectangularBoundaryS(locationP, locationTypeP, dimensionDP);

    }


    private static StrategyDefinitionParameter createRectangularBoundaryS(Parameter locationP, Parameter locationTypeP, Parameter dimensionP) {
        Class boundaryClass = BoundaryFactory.getBoundaryClassForShape(BoundaryShape.RECTANGULAR);
        StrategyDefinitionParameter boundaryS = new StrategyDefinitionParameter(S_RECTANGULAR, boundaryClass.getName());
        boundaryS.addParameter(locationP);
        boundaryS.addParameter(locationTypeP);
        boundaryS.addParameter(dimensionP);

        List boundsSourceParams = RectangularBoundaryBoundsCalculation.createSourceParameters(boundaryS);
        DerivedParameter boundsDP = new DerivedParameter(P_BOUNDS, boundsSourceParams, new RectangularBoundaryBoundsCalculation());


        boundaryS.addParameter(boundsDP);


        return boundaryS;
    }

    public BoundaryShape getBoundaryShape() {
        return BoundaryShape.RECTANGULAR;
    }

    public RectangularBoundaryStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);


    }

    public void parameterValueChanged(ParameterValueChangedEvent event) {
        super.parameterValueChanged(event);

    }


    public boolean isDimensionsDerived() {
        return (getParameter(P_DIMENSIONS) instanceof DerivedParameter);
    }

    public CartesianDimensions getDimensions() {
        return (CartesianDimensions)super.getParameter(P_DIMENSIONS).getValue();
    }


    public void setDimensions(CartesianDimensions dimensions) {
        super.setParameterValue(P_DIMENSIONS, dimensions);
    }

    public Parameter getDimensionsP() {
        return super.getParameter(P_DIMENSIONS);
    }


    private static final Logger log = Logger.getLogger(RectangularBoundaryStrategy.class);
    public static final String S_RECTANGULAR = "rectangularBoundaryStrategy";


    public static final String P_DIMENSIONS = "dimensions";


}
