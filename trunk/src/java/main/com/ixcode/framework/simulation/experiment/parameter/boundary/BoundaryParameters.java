/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary;

import com.ixcode.bugsim.model.agent.boundary.CircularBoundaryAgentFactory;
import com.ixcode.bugsim.model.agent.boundary.LinearBoundaryAgentFactory;
import com.ixcode.bugsim.model.agent.boundary.NullBoundaryFactory;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.ExtentStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.FixedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.RectangularDistancedExtentBoundsCalculator;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryFactory;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryParameters {


    public static StrategyDefinitionParameter createDistancedBoundaryStrategy(Parameter sourceBounds, BoundaryShape sourceShape, double distance, BoundaryShape shape) {

        Class boundaryClass = BoundaryFactory.getBoundaryClassForShape(shape);
        StrategyDefinitionParameter distanced = new StrategyDefinitionParameter(DistancedExtentStrategy.S_DISTANCED_EXTENT, boundaryClass.getName());

        DerivedParameter derivedBounds = new DerivedParameter(DistancedExtentStrategy.P_INNER_BOUNDARY, new DirectDerivationCalculation());
        derivedBounds.addSourceParameter(sourceBounds);
        distanced.addParameter(derivedBounds);

        Parameter shapeParam = new Parameter(BoundaryParameters.P_BOUNDARY_SHAPE, shape);
        distanced.addParameter(shapeParam);

        Parameter sourceShapeParam = new Parameter(SOURCE_SHAPE, sourceShape);
        distanced.addParameter(sourceShapeParam);

        Parameter distanceParam = new Parameter(DistancedExtentStrategy.P_DISTANCE, distance);
        distanced.addParameter(distanceParam);

        DerivedParameter boundaryBoundsDP = new DerivedParameter(BoundaryParameters.P_BOUNDARY_BOUNDS, RectangularDistancedExtentBoundsCalculator.INSTANCE);
        boundaryBoundsDP.addSourceParameter(derivedBounds);
        boundaryBoundsDP.addSourceParameter(distanceParam);
        boundaryBoundsDP.addSourceParameter(shapeParam);
        boundaryBoundsDP.addSourceParameter(sourceShapeParam);

        distanced.addParameter(boundaryBoundsDP);

        return distanced;
    }

    public static StrategyDefinitionParameter createProportionalBoundaryStrategy(Parameter cabbageRectangle, double p, BoundaryShape shape) {

        Class boundaryClass = BoundaryFactory.getBoundaryClassForShape(shape);

        StrategyDefinitionParameter proportionalBorder = new StrategyDefinitionParameter(PROPORTIONAL_BOUNDARY, boundaryClass.getName());

        final DerivedParameter sourceRectP = new DerivedParameter(DistancedExtentStrategy.P_INNER_BOUNDARY, new DirectDerivationCalculation());
        sourceRectP.addSourceParameter(cabbageRectangle);
        proportionalBorder.addParameter(sourceRectP);

        proportionalBorder.addParameter(new Parameter(BoundaryParameters.P_BOUNDARY_SHAPE, shape));

        final Parameter proportionP = new Parameter(PROPORTION, p);
        proportionalBorder.addParameter(proportionP);

        final String  sourceRectName = sourceRectP.getName();
        final String sourceProportionName = proportionP.getName();

        DerivedParameter borderRect = new DerivedParameter(BoundaryParameters.P_BOUNDARY_BOUNDS, new IDerivedParameterCalculation() {
            public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
                forwardingMap.addForward(sourceRectName);
                forwardingMap.addForward(sourceProportionName);
            }

            public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
                Parameter sourceRectP = sourceParams.getParameter(sourceRectName);
                Parameter proportionP = sourceParams.getParameter(sourceProportionName);
                CartesianBounds sourceBounds = (CartesianBounds)sourceRectP.getValue();
                double p = proportionP.getDoubleValue();
                double width = sourceBounds.getDoubleWidth() * p;
                double height = sourceBounds.getDoubleHeight() * p;
                return new CartesianBounds(0, 0, width, height);
            }
        });
        borderRect.addSourceParameter(sourceRectP);
        borderRect.addSourceParameter(proportionP);

        proportionalBorder.addParameter(borderRect);

        return proportionalBorder;
    }


    public static ParameterManipulation createFixedBoundaryManipulation(Parameter boundaryDefinition, double dimension, BoundaryShape shape) {
        StrategyDefinitionParameter shapeS = RectangularBoundaryStrategy.createRectangularBoundaryS(new CartesianDimensions(dimension));
        StrategyDefinitionParameter fixedBoundary = FixedExtentStrategy.createFixedExtentStrategyP(shapeS);
        return new ParameterManipulation(boundaryDefinition, fixedBoundary);
    }


    /**
     * Creates a border which is X bigger than the cabbage patch.
     *
     * @param
     * @param
     * @param distance
     * @return
     */
    public static ParameterManipulation createDistancedManipulation(Parameter boundaryDefinition, double distance, Parameter sourceBounds, BoundaryShape shape, BoundaryShape sourceShape) {
        StrategyDefinitionParameter distancedBoundary = createDistancedBoundaryStrategy(sourceBounds, sourceShape, distance, shape);
        ParameterManipulation manipBoundaryDef = new ParameterManipulation(boundaryDefinition, distancedBoundary);
        return manipBoundaryDef;
    }

    public static ParameterManipulation createProportionalBoundaryManipulation(Parameter boundaryDefinition, double proportion, Parameter cabbageRectangle, BoundaryShape shape) {

        StrategyDefinitionParameter dynamicBoundary = createProportionalBoundaryStrategy(cabbageRectangle, proportion, shape);
        return new ParameterManipulation(boundaryDefinition, dynamicBoundary);


    }


    public static void addDistancedBoundary(Parameter releaseBorder, double landscapeD, double releaseD, Parameter cabbageRectangle, Parameter landscapeBorder, List manipulations, boolean structureChanged, BoundaryShape boundaryShape) {
        BoundaryParameters.addDistancedBoundaryManipulation(releaseBorder, landscapeD, releaseD, cabbageRectangle, landscapeBorder, manipulations, boundaryShape, boundaryShape, BoundaryShape.RECTANGULAR, structureChanged);
    }

    public static void addDistancedBoundaryManipulation(Parameter releaseBorderP, double landscapeDistance, double releaseDistance, Parameter cabbageBounds, Parameter landscapeBorder, List manipulations, BoundaryShape shapeLandscape, BoundaryShape shapeBirth, BoundaryShape cabbageShape, boolean changesParameterStructure) {
        ParameterManipulation releaseDistancedManip = createDistancedManipulation(releaseBorderP, releaseDistance, cabbageBounds, shapeBirth, cabbageShape);

        StrategyDefinitionParameter releaseBorderParam = (StrategyDefinitionParameter)releaseDistancedManip.getValueToSet();
        Parameter releaseBoundaryBounds = releaseBorderParam.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);

        ParameterManipulation landscapeDistancedManip = createDistancedManipulation(landscapeBorder, landscapeDistance, releaseBoundaryBounds, shapeLandscape, shapeBirth);

        DistanceBoundaryManipulation distancedManip = new DistanceBoundaryManipulation(changesParameterStructure, landscapeDistance, releaseDistance);
        distancedManip.addParameterManipulation(landscapeDistancedManip);
        distancedManip.addParameterManipulation(releaseDistancedManip);
        manipulations.add(distancedManip);
    }

    public static CartesianDimensions getBoundaryDimensions(StrategyDefinitionParameter strategyP) {
        return (CartesianDimensions)strategyP.findParameter(P_BOUNDARY_DIMENSION).getValue();
    }

    public static String getBoundaryName(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(ExtentStrategyBase.P_STRATEGY_NAME).getStringValue();
    }

    public static Parameter getBoundaryBoundsP(StrategyDefinitionParameter boundaryStrategy) {
        return boundaryStrategy.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);
    }


    private static class DistanceBoundaryManipulation extends MultipleParameterManipulation {

        public DistanceBoundaryManipulation(boolean changesParameterStructure, double landscapeDistance, double birth) {
            super();
            _landscapeDistance = landscapeDistance;
            _birthDistance = birth;
        }

        public String toString() {
            return "Boundary Distance: landscape=" + D2.format(_landscapeDistance) + ", release=" + D2.format(_birthDistance) + ", structureChanged: " + super.isParameterStructureChanged();
        }

        private DecimalFormat D2 = new DecimalFormat("0.00");
        private double _landscapeDistance;
        private double _birthDistance;
    }

    public static void addProportionalRectangleBoundaryManipulation(Parameter landscapeBorder, double propLandscape, double propBirth, Parameter cabbageRectangle, Parameter birthBorder, List manipulations, BoundaryShape shapeLandscape, BoundaryShape shapeBirth) {
        ParameterManipulation landscapePropManip = createProportionalBoundaryManipulation(landscapeBorder, propLandscape, cabbageRectangle, shapeLandscape);
        ParameterManipulation birthPropManip = createProportionalBoundaryManipulation(birthBorder, propBirth, cabbageRectangle, shapeBirth);

        MultipleParameterManipulation proportionalManip = new MultipleParameterManipulation();
        proportionalManip.addParameterManipulation(landscapePropManip);
        proportionalManip.addParameterManipulation(birthPropManip);
        manipulations.add(proportionalManip);
    }


    public static void addFixedRectangleBoundaryManipulation(Parameter landscapeBorder, double landscape, double birth, Parameter birthBorder, List manipulations, BoundaryShape shapeLandscape, BoundaryShape shapeBirth) {
        ParameterManipulation landscapeFixedManip = createFixedBoundaryManipulation(landscapeBorder, landscape, shapeLandscape);
        ParameterManipulation birthFixedManip = createFixedBoundaryManipulation(birthBorder, birth, shapeBirth);

        MultipleParameterManipulation fixedManip = new MultipleParameterManipulation();
        fixedManip.addParameterManipulation(landscapeFixedManip);
        fixedManip.addParameterManipulation(birthFixedManip);
        manipulations.add(fixedManip);
    }

    /**
     * You must pass in a location parameter created with the LandscapeParameters class.
     *
     * @param params
     * @param length
     * @param locationP
     * @return
     */
    public static StrategyDefinitionParameter createLinearBoundaryAgentStrategy(String name, CartesianDimensions dimensions, Parameter locationP) {
        StrategyDefinitionParameter boundaryStrategy = new StrategyDefinitionParameter(P_LINEAR_BOUNDARY, LinearBoundaryAgentFactory.class.getName());
        Parameter nameP = new Parameter(ExtentStrategyBase.P_STRATEGY_NAME, name);
        boundaryStrategy.addParameter(nameP);

        Parameter lengthP = new Parameter(P_BOUNDARY_LENGTH, dimensions.getDoubleWidth());
        boundaryStrategy.addParameter(locationP);
        boundaryStrategy.addParameter(lengthP);

        return boundaryStrategy;
    }

    private static StrategyDefinitionParameter createCircularBoundaryAgentStrategy(String name, CartesianDimensions dimensions, Parameter locationP) {
        StrategyDefinitionParameter boundaryStrategy = new StrategyDefinitionParameter(P_CIRCULAR_BOUNDARY, CircularBoundaryAgentFactory.class.getName());
        Parameter nameP = new Parameter(ExtentStrategyBase.P_STRATEGY_NAME, name);
        boundaryStrategy.addParameter(nameP);

        Parameter dimensionP = new Parameter(P_BOUNDARY_DIMENSION, dimensions);
        boundaryStrategy.addParameter(locationP);
        boundaryStrategy.addParameter(dimensionP);
        return boundaryStrategy;
    }

    public static double getLinearBoundaryLength(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(P_BOUNDARY_LENGTH).getDoubleValue();
    }

    public static void addBoundaryManipulations(ExperimentPlan plan, Parameter boundaryContainer, String[] names, BoundaryShape[] shapes, CartesianDimensions[] dimensions, Parameter[] locations) {
        List manips = new ArrayList();
        for (int i = 0; i < shapes.length; ++i) {
            StrategyDefinitionParameter boundaryStrategy = createBoundaryStrategy(names[i], shapes[i], dimensions[i], locations[i]);
            ParameterManipulation manip = new ParameterManipulation(boundaryContainer, boundaryStrategy);
            manips.add(manip);

        }
        plan.addParameterManipulationSequence(manips);
    }

    public static StrategyDefinitionParameter createBoundaryStrategy(String name, BoundaryShape shape, CartesianDimensions dimensions, Parameter locationP) {
        StrategyDefinitionParameter strategyP = null;
        if (shape == BoundaryShape.LINEAR) {
            strategyP = createLinearBoundaryAgentStrategy(name, dimensions, locationP);
        } else if (shape == BoundaryShape.CIRCULAR) {
            strategyP = createCircularBoundaryAgentStrategy(name, dimensions, locationP);
        } else {
            throw new IllegalArgumentException("Cannot create boundaries of shape: " + shape);
        }


        return strategyP;
    }

    public static StrategyDefinitionParameter createNullBoundaryStrategy() {
        StrategyDefinitionParameter boundaryStrategy = new StrategyDefinitionParameter(P_NULL_BOUNDARY, NullBoundaryFactory.class.getName());

        return boundaryStrategy;
    }

    private static final String P_NULL_BOUNDARY = "noBoundary";

    private static final String P_LINEAR_BOUNDARY = "linearBoundary";
    private static final String P_BOUNDARY_LENGTH = "length";

    private static final String P_CIRCULAR_BOUNDARY = "circularBoundary";
    private static final String P_BOUNDARY_DIMENSION = "dimension";


    public static final String PROPORTION = "proportion";
    public static final String P_BOUNDARY = "boundary";
    public static final String PROPORTIONAL_BOUNDARY = "proportionalBorder";
    private static final String SOURCE_SHAPE = "sourceShape";
    public static final String P_RELEASE_ZONE_BOUNDARY = "releaseZoneBoundary";

    public static final String P_BOUNDARY_BOUNDS = "boundaryBounds";
    public static final String P_BOUNDARY_SHAPE = "boundaryShape";
}
