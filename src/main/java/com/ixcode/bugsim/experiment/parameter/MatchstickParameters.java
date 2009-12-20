/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter;

import com.ixcode.bugsim.agent.matchstick.MatchstickFactory;
import com.ixcode.bugsim.agent.matchstick.RandomMatchstickFactory;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo apply this pattern to all the other parameter types.
 */
public class MatchstickParameters {


    public static void addMatchstickParameters(ParameterMap params, String boundaryName, double matchstickLength) {
        Category matchstickC = new Category(CAT_MATCHSTICK);

        StrategyDefinitionParameter creationStrategyP = createCentredMatchstickCreationStrategyP(90d,270d,1d,10d);
        Parameter creationP = new Parameter(P_MATCHSTICK_CREATION, creationStrategyP);

        Parameter lengthP = new Parameter(P_LENGTH, matchstickLength);

        Parameter locationP = LandscapeParameters.createLandscapeCentredLocation(params, 0d, 0d);
        Parameter boundaryStrategyP = BoundaryParameters.createLinearBoundaryAgentStrategy(boundaryName, new CartesianDimensions(100d), locationP);
        Parameter ixBoundaryP = new Parameter(P_MATCHSTICK_INTERSECTION_BOUNDARY, boundaryStrategyP);




        matchstickC.addParameter(creationP);
        matchstickC.addParameter(lengthP);
        matchstickC.addParameter(ixBoundaryP);

        params.addCategory(matchstickC);

    }

    private static StrategyDefinitionParameter createCentredMatchstickCreationStrategyP(double startH, double endH, double hIncr, double dropD) {
        StrategyDefinitionParameter creationStrategyP = new StrategyDefinitionParameter(P_CENTRED_CREATION, MatchstickFactory.class.getName());

        Parameter startHeading = new Parameter(P_START_HEADING, startH);
        Parameter endHeading = new Parameter(P_END_HEADING, endH);
        Parameter headingIncrement = new Parameter(P_HEADING_INCREMENT, hIncr);
        Parameter droppingDistance = new Parameter(P_DROPPING_DISTANCE, dropD);


        creationStrategyP.addParameter(startHeading);
        creationStrategyP.addParameter(endHeading);
        creationStrategyP.addParameter(headingIncrement);
        creationStrategyP.addParameter(droppingDistance);
        return creationStrategyP;
    }

    public static StrategyDefinitionParameter createRandomMatchstickCreationStrategyP(long populationSize, String boundaryName, BoundaryShape boundaryShape, CartesianDimensions boundaryDimensions, Parameter boundaryLocationP) {
        StrategyDefinitionParameter creationStrategyP = new StrategyDefinitionParameter(P_RANDOM_CREATION, RandomMatchstickFactory.class.getName());

        Parameter populationSizeP = new Parameter(P_POPULATION_SIZE, populationSize);
        Parameter outerReleaseBoundaryP = new Parameter(P_MATCHSTICK_OUTER_BOUNDARY, BoundaryParameters.createBoundaryStrategy(boundaryName, boundaryShape, boundaryDimensions, boundaryLocationP));

        creationStrategyP.addParameter(outerReleaseBoundaryP);
        creationStrategyP.addParameter(populationSizeP);
        return creationStrategyP;
    }

    public static String getIntersectionBoundaryName(ParameterMap params) {
        StrategyDefinitionParameter boundaryStrategeyP = (StrategyDefinitionParameter)getIntersectionBoundaryP(params).getValue();
        return BoundaryParameters.getBoundaryName(boundaryStrategeyP);
    }


    public static StrategyDefinitionParameter getMatchstickCreationStrategyP(ParameterMap params) {
        return (StrategyDefinitionParameter)params.findParameter(CAT_MATCHSTICK + "." + P_MATCHSTICK_CREATION).getValue();
    }

    public static Parameter getIntersectionBoundaryP(ParameterMap params) {
        return params.findParameter(CAT_MATCHSTICK + "." + P_MATCHSTICK_INTERSECTION_BOUNDARY);
    }


    public static double getMatchstickLength(ParameterMap params) {
        return params.findParameter(CAT_MATCHSTICK + "." + P_LENGTH).getDoubleValue();
    }

    public static double getMatchstickDroppingDistance(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(P_DROPPING_DISTANCE).getDoubleValue();
    }

    public static double getStartHeading(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(P_START_HEADING).getDoubleValue();
    }

    public static double getEndHeading(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(P_END_HEADING).getDoubleValue();
    }

    public static double getHeadingIncrement(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(P_HEADING_INCREMENT).getDoubleValue();
    }

    public static void addDroppingDistanceManipulations(ExperimentPlan plan, double[] values) {
        SimulationParameters.addManipulation(plan, CAT_MATCHSTICK + "." + P_MATCHSTICK_CREATION + "." + P_CENTRED_CREATION + "." + P_DROPPING_DISTANCE, values);
    }


    public static void addRandomMatchstickCreationManipulations(ExperimentPlan plan, long[] populationSizes, String[] boundaryNames, BoundaryShape[] boundaryShapes, CartesianDimensions[] boundaryDimensions, Parameter[] boundaryLocations) {
        Parameter createP = getCreateParameter(plan.getParameterTemplate());

        List manips = new ArrayList();
        for (int i=0;i<populationSizes.length;++i) {
            StrategyDefinitionParameter randomStrategyP = createRandomMatchstickCreationStrategyP(populationSizes[i], boundaryNames[i], boundaryShapes[i], boundaryDimensions[i ], boundaryLocations[i]);
            ParameterManipulation manip = new ParameterManipulation(createP, randomStrategyP); //@todo need to notify that the structure has changed
            manips.add(manip);
        }

        plan.addParameterManipulationSequence(manips);
    }

    private static Parameter getCreateParameter(ParameterMap params) {
        return params.findParameter(CAT_MATCHSTICK + "." + P_MATCHSTICK_CREATION);
    }

    public static StrategyDefinitionParameter getOuterReleaseBoundaryP(StrategyDefinitionParameter strategyParameter) {
        return (StrategyDefinitionParameter)strategyParameter.findParameter(P_MATCHSTICK_OUTER_BOUNDARY).getValue();
    }


    public static long getPopulationSize(StrategyDefinitionParameter algorithmParameter) {
        return algorithmParameter.findParameter(P_POPULATION_SIZE).getLongValue();
    }


    public static final String CAT_MATCHSTICK = "matchstick";

    private static final String P_MATCHSTICK_CREATION = "creation";
    private static final String P_CENTRED_CREATION = "centred";

    private static final String P_RANDOM_CREATION = "random";
    private static final String P_POPULATION_SIZE = "populationSize";


    private static final String P_START_HEADING = "startHeading";
    private static final String P_END_HEADING = "endHeading";
    private static final String P_HEADING_INCREMENT = "headingIncrement";
    private static final String P_DROPPING_DISTANCE = "droppingDistance";

    private static final String P_LENGTH = "length";


    private static final String P_MATCHSTICK_INTERSECTION_BOUNDARY = "intersectionBoundary";
    private static final String P_MATCHSTICK_OUTER_BOUNDARY = "outerReleaseBoundary";
}
