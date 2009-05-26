/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.parameter;

import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.MultipleParameterManipulation;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterManipulation;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimulationParameters {




    public static void configureSimulationParameters(ExperimentPlan plan, long numberOfReplicants) {
        SimulationCategory simC = new SimulationCategory(plan.getParameterTemplate(), false);


        simC.setNumberOfReplicates(numberOfReplicants);

        simC.setArchiveEscapedAgents(false);


        // @todo This should be moved into the ButterflyCategory!
        Parameter lifehistory = plan.getParameterTemplate().findParameter(ButterflyParameters.BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY);
        lifehistory.setValue(new Boolean(false));
    }

    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, BoundaryShape boundaryShape) {
        addReleaseBoundaryManipulations(plan, releaseDistances, new double[releaseDistances.length], boundaryShape);
    }

    /**
     * Manipulates the release boundary and landscape sizes.
     *
     * @param plan
     */
    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, double[] landscapeDistances, BoundaryShape boundaryShape) {
        Parameter cabbageRectangle = CabbageParameters.getCabbagePatchBoundsP(plan.getParameterTemplate());
        Parameter landscapeBorder = plan.getParameterTemplate().findParameter(LandscapeParameters.ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
        Parameter releaseBorder = ButterflyParameters.getReleaseBoundaryParameter(plan);

        List manipulations = new ArrayList();

        for (int i = 0; i < releaseDistances.length; ++i) {
            BoundaryParameters.addDistancedBoundary(releaseBorder, landscapeDistances[i], releaseDistances[i], cabbageRectangle, landscapeBorder, manipulations, false, boundaryShape);
        }


        plan.addParameterManipulationSequence(manipulations);

    }

    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, long[] maxButterflies, BoundaryShape boundaryShape) {
        addReleaseBoundaryManipulations(plan, releaseDistances, new boolean[releaseDistances.length] , maxButterflies, boundaryShape);

    }
    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, double[] landscapeDistances, long[] maxButterflies, BoundaryShape boundaryShape) {
        addReleaseBoundaryManipulations(plan, releaseDistances, landscapeDistances,  new boolean[releaseDistances.length] , maxButterflies, boundaryShape);

    }
    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, boolean[] rfmls, long[] maxButterflies, BoundaryShape boundaryShape) {
        addReleaseBoundaryManipulations(plan, releaseDistances, new double[releaseDistances.length], rfmls, maxButterflies, boundaryShape);
    }



    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, double[] landscapeDistances, boolean[] rfmls, long[] maxButterflies, BoundaryShape boundaryShape) {
        addReleaseBoundaryManipulations(plan, releaseDistances, landscapeDistances, rfmls, maxButterflies, new StrategyDefinitionParameter[]{}, boundaryShape);
    }

    public static void addReleaseBoundaryManipulations(ExperimentPlan plan, double[] releaseDistances, double[] landscapeDistances, boolean[] rfmls, long[] maxButterflies, StrategyDefinitionParameter[] releaseStrategies, BoundaryShape boundaryShape) {
        throw new IllegalStateException("Method is no longer supported");
//        Parameter cabbageRectangle = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_BOUNDS);
//        Parameter landscapeBorder = plan.getParameterTemplate().findParameter(LandscapeParameters.ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
//
//
//        Parameter maxButterfliesP = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_BUTTERFLIES);
//        Parameter releaseP = ButterflyParameters.getReleaseP(plan.getParameterTemplate());
//
//
//        List boundaryManipulations = new ArrayList();
//
//        for (int i = 0; i < releaseDistances.length; ++i) {
//            Parameter releaseBorder = plan.getParameterTemplate().findParameter(ButterflyParameters.P_BUTTERFLY_AGENT_LIFECYCLE_RELEASE_BORDER_DEF);
//            if (releaseStrategies.length > 0) {
//                releaseBorder = ButterflyParameters.getReleaseBoundaryParameter(releaseStrategies[i]);
//            }
//
//            BoundaryParameters.addDistancedBoundary(releaseBorder, landscapeDistances[i], releaseDistances[i], cabbageRectangle, landscapeBorder, boundaryManipulations, false, boundaryShape);
//        }
//
//        List manipulations = new ArrayList();
//        for (Iterator itr = boundaryManipulations.iterator(); itr.hasNext();) {
//            MultipleParameterManipulation boundaryManipulation = (MultipleParameterManipulation)itr.next();
//            int i = boundaryManipulations.indexOf(boundaryManipulation);
//
//            ParameterManipulation rfmlManipulation = ButterflyParameters.createRFMLManipulation(plan.getParameterTemplate(), rfmls[i]);
//            ParameterManipulation maxBManipulation = new ParameterManipulation(maxButterfliesP, maxButterflies[i]);
//
//
////            boolean structureChanged = (releaseStrategies.length >0) ?  true : boundaryManipulation.isParameterStructureChanged();
//            MultipleParameterManipulation multipleManipulation = new MultipleParameterManipulation();
//
//            if (releaseStrategies.length > 0) {
//                ParameterManipulation releaseStrategyManipulation = new ParameterManipulation(releaseP, releaseStrategies[i]);
//                multipleManipulation.addParameterManipulation(releaseStrategyManipulation);
//            }
//
//            multipleManipulation.addParameterManipulation(boundaryManipulation);
//            multipleManipulation.addParameterManipulation(rfmlManipulation);
//            multipleManipulation.addParameterManipulation(maxBManipulation);
//
//
//            manipulations.add(multipleManipulation);
//        }
//
//
//        plan.addParameterManipulationSequence(manipulations);

    }

    /**
     * @todo get  the shapes from the landscape and cabbage patch parameters
     * @param plan
     * @param landscapeDistance
     * @param landscapeShape
     * @param patchShape
     */
    public static void addLandscapeBoundaryManipulations(ExperimentPlan plan, double landscapeDistance, BoundaryShape landscapeShape, BoundaryShape patchShape) {

        Parameter cabbagePatchBounds = CabbageParameters.getCabbagePatchBoundsP(plan.getParameterTemplate());
        Parameter landscapeBoundaryDefinition = LandscapeParameters.getLandscapeBoundaryDefinitionP(plan.getParameterTemplate());

        ParameterManipulation landscapeDistancedManip = BoundaryParameters.createDistancedManipulation(landscapeBoundaryDefinition, landscapeDistance, cabbagePatchBounds, landscapeShape, patchShape);

        List manipulations = new ArrayList();
        manipulations.add(landscapeDistancedManip);
        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addCabbageRadiusManipulation(ExperimentPlan plan, double[] values) {
        addManipulation(plan, CabbageParameters.ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED_CABBAGE_RADIUS, values);
    }

    public static void addSeparationLengthManipulations(ExperimentPlan plan) {
        List manipulations = new ArrayList();
        Parameter iterEdgeSeparation = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_INTER_EDGE_SEPARATION);
        Parameter moveLength = plan.getParameterTemplate().findParameter(ButterflyParameters.MOVEMENT_BEHAVIOUR_RANDOMWALK_MOVE_LENGTH);

        MultipleParameterManipulation SA1 = new MultipleParameterManipulation();
        SA1.addParameterManipulation(new ParameterManipulation(iterEdgeSeparation, 10d));
        SA1.addParameterManipulation(new ParameterManipulation(moveLength, 1L));

        manipulations.add(SA1);

        MultipleParameterManipulation SA2 = new MultipleParameterManipulation();
        SA2.addParameterManipulation(new ParameterManipulation(iterEdgeSeparation, 10d));
        SA2.addParameterManipulation(new ParameterManipulation(moveLength, 60L));

        manipulations.add(SA2);

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addInterEdgeSeparationManipulation(ExperimentPlan plan, double[] values) {
        addManipulation(plan, CabbageParameters.LAYOUT_EDGE_EFFECT_INTER_EDGE_SEPARATION, values);
    }

    public static void addManipulation(ExperimentPlan plan, String paramName, long[] values) {
        List manipulations = new ArrayList();
        Parameter manipulatedParam = plan.getParameterTemplate().findParameter(paramName);


        for (int i = 0; i < values.length; ++i) {
            manipulations.add(new ParameterManipulation(manipulatedParam, values[i]));
        }

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addManipulation(ExperimentPlan plan, String paramName, double[] values) {
        List manipulations = new ArrayList();
        Parameter parameter = plan.getParameterTemplate().findParameter(paramName);

        if (parameter == null) {
            throw new RuntimeException("Could not find parameter '" + paramName + "'");
        }

        for (int i = 0; i < values.length; ++i) {
            manipulations.add(new ParameterManipulation(parameter, values[i]));
        }

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addMultipleManipulation(ExperimentPlan plan, String paramNameA, String paramNameB, double[] valuesA, double[] valuesB) {
        List manipulations = new ArrayList();
        Parameter parameterA = plan.getParameterTemplate().findParameter(paramNameA);
        Parameter parameterB = plan.getParameterTemplate().findParameter(paramNameB);

        if (parameterA == null || parameterB == null) {
            throw new RuntimeException("Could not find parameter '" + paramNameA + "' or parameter '" + paramNameB + "'");
        }

        for (int i = 0; i < valuesA.length; ++i) {
            MultipleParameterManipulation manip = new MultipleParameterManipulation();
            manip.addParameterManipulation(new ParameterManipulation(parameterA, valuesA[i]));
            manip.addParameterManipulation(new ParameterManipulation(parameterB, valuesB[i]));
            manipulations.add(manip);
        }

        plan.addParameterManipulationSequence(manipulations);
    }




}
