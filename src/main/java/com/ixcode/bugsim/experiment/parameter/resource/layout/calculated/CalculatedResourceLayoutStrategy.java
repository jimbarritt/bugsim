/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.layout.calculated;

import com.ixcode.bugsim.agent.cabbage.layout.CalculatedResourceLayout;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.math.geometry.CoordinateDistributor;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;

import java.util.List;


/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 11:46:32 AM by jim
 */
public class CalculatedResourceLayoutStrategy extends ResourceLayoutStrategyBase {
    public CalculatedResourceLayoutStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }

    public CalculatedResourceLayoutStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(10, 10, CalculatedResourceLayoutType.CORNER_CENTRE, 0);
    }

    public static StrategyDefinitionParameter createStrategyS(double radius, double interEdgeSeparation, CalculatedResourceLayoutType layoutType, long expectedEggCount) {
        StrategyDefinitionParameter strategyS = new StrategyDefinitionParameter(STRATEGY_NAME, CalculatedResourceLayout.class.getName());

        Parameter radiusP = new Parameter(P_RESOURCE_RADIUS, radius);
        strategyS.addParameter(radiusP);
        Parameter interEdgeSeparationP = new Parameter(P_INTER_EDGE_SEPARATION, interEdgeSeparation);
        strategyS.addParameter(interEdgeSeparationP);
        strategyS.addParameter(new Parameter(P_CALCULATED_LAYOUT_TYPE, layoutType));

        List calcParams = CalculatedPatchDimensionsCalculation.createParams(radiusP, interEdgeSeparationP);
        IDerivedParameterCalculation patchBoundsCalc = new CalculatedPatchDimensionsCalculation();
        StrategyDefinitionParameter boundaryS = RectangularBoundaryStrategy.createCalculatedDimensionBoundaryS(patchBoundsCalc, calcParams);

        strategyS.addParameter(new Parameter(P_LAYOUT_BOUNDARY, boundaryS));

        strategyS.addParameter(new Parameter(P_LAYOUT_NAME, layoutType.getName()));
        strategyS.addParameter(new Parameter(P_EXPECTED_EGG_COUNT, expectedEggCount));
        return strategyS;
    }


    public Parameter getResourceRadiusP() {
        return super.getParameter(P_RESOURCE_RADIUS);
    }
    public double getResourceRadius() {
        return super.getParameter(P_RESOURCE_RADIUS).getDoubleValue();
    }

    public void setResourceRadius(double radius) {
        super.getParameter(P_RESOURCE_RADIUS).setValue(radius);
    }

    public Parameter getInterEdgeSeparationP() {
        return super.getParameter(P_INTER_EDGE_SEPARATION);
    }

    public double getInterEdgeSeparation() {
        return super.getParameter(P_INTER_EDGE_SEPARATION).getDoubleValue();
    }

    public void setInterEdgeSeparation(double separation) {
        super.getParameter(P_INTER_EDGE_SEPARATION).setValue(separation);
    }

    public CalculatedResourceLayoutType getCalculatedLayoutType() {
        return (CalculatedResourceLayoutType)super.getParameter(P_CALCULATED_LAYOUT_TYPE).getValue();
    }

    public void setCalculatedLayoutType(CalculatedResourceLayoutType type) {
        super.getParameter(P_CALCULATED_LAYOUT_TYPE).setValue(type);
        super.getParameter(P_LAYOUT_NAME).setValue(type.getName());
    }

    public boolean isBoundaryDimensionDerived() {
        BoundaryStrategyBase boundary = getLayoutBoundary();
        boolean isDerived = false;

        if (boundary instanceof RectangularBoundaryStrategy) {
            isDerived = ((RectangularBoundaryStrategy)boundary).isDimensionsDerived();
        } else {
            isDerived = ((CircularBoundaryStrategy)boundary).isRadiusDerived();
        }
        return isDerived;
    }

    public void setBoundaryDimensionDerived(boolean derived) {
        Parameter oldLocationP = getLayoutBoundary().getLocationP();

        if (derived) {
            List calcParams = CalculatedPatchDimensionsCalculation.createParams(getResourceRadiusP(), getInterEdgeSeparationP());
            IDerivedParameterCalculation patchBoundsCalc = new CalculatedPatchDimensionsCalculation();
            StrategyDefinitionParameter boundaryS = RectangularBoundaryStrategy.createCalculatedDimensionBoundaryS(patchBoundsCalc, calcParams);
            super.replaceStrategyDefinitionParameter(P_LAYOUT_BOUNDARY,boundaryS);
        } else {
            StrategyDefinitionParameter boundaryS = RectangularBoundaryStrategy.createRectangularBoundaryS(new CartesianDimensions(100));
            super.replaceStrategyDefinitionParameter(P_LAYOUT_BOUNDARY, boundaryS);
        }
        if (oldLocationP.isDerived()) {
            Parameter newLocationP = getLayoutBoundary().getLocationP();
            getLayoutBoundary().getStrategyS().replaceParameter(newLocationP, oldLocationP);
        }
    }


    public static final String STRATEGY_NAME = "calculatedLayout";

    public static final String P_RESOURCE_RADIUS = "resourceRadius";
    public static final String P_BOUNDARY_DIMENSION_DERIVED = "boundaryDimensionDerived";
    public static final String P_INTER_EDGE_SEPARATION = "interEdgeSeparation";
    public static final String P_CALCULATED_LAYOUT_TYPE = "calculatedLayoutType";

}
