/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter;

import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.bugsim.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterManipulation;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.ArrayList;
import java.util.List;

/**                                     
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DebugParameters {

    public static void addManipulations(ExperimentPlan plan) {
        Parameter layoutType = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_TYPE);
        layoutType.setValue(CalculatedResourceLayoutType.CORNER_CENTRE);


//        Parameter populationSize = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_POPULATION_SIZE);
//        populationSize.setValue(new Long(300));
//
//        Parameter maxButterflies = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_BUTTERFLIES);
//        maxButterflies.setValue(1000);

//        Parameter numberOfReplicants = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_NUMBER_OF_REPLICANTS);
//        numberOfReplicants.setValue(new Long(100));
//
//
//
//        Parameter includeEscaped = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_ARCHIVE_ESCAPED_AGENTS);
//        includeEscaped.setValue(new Boolean(false));

        Parameter rfml = plan.getParameterTemplate().findParameter(ButterflyParameters.MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH);
        rfml.setValue(new Boolean(false));

        Parameter lifehistory = plan.getParameterTemplate().findParameter(ButterflyParameters.BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY);
        lifehistory.setValue(new Boolean(false));

        addInterEdgeSeparationManipulation(plan);
        addCabbageRadiusManipulation(plan);
        addAngleOfTurnManipulation(plan);
        addMoveLengthManipulation(plan);
        addBoundaryManipulations(plan);
    }

    /**
     * Manipulates the release boundary and landscape sizes.
     *
     * @param plan
     */
    public static void addBoundaryManipulations(ExperimentPlan plan) {
        Parameter cabbageRectangle = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_BOUNDS);
        Parameter landscapeBorder = plan.getParameterTemplate().findParameter(LandscapeParameters.ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
        Parameter birthBorder = plan.getParameterTemplate().findParameter(ButterflyParameters.P_BUTTERFLY_AGENT_LIFECYCLE_RELEASE_BORDER_DEF);

        List manipulations = new ArrayList();

        addDistancedBoundary(birthBorder, 0, 0, cabbageRectangle, landscapeBorder, manipulations, false);
        addDistancedBoundary(birthBorder, 0, 5, cabbageRectangle, landscapeBorder, manipulations, false);
        addDistancedBoundary(birthBorder, 0, 10, cabbageRectangle, landscapeBorder, manipulations, false);
        addDistancedBoundary(birthBorder, 0, 20, cabbageRectangle, landscapeBorder, manipulations, false);
        addDistancedBoundary(birthBorder, 0, 100, cabbageRectangle, landscapeBorder, manipulations, false);
//        addDistancedBoundary(birthBorder, 0, 150, cabbageRectangle, landscapeBorder, manipulations, false);
//        addDistancedBoundary(birthBorder, 0, 200, cabbageRectangle, landscapeBorder, manipulations, false);

        plan.addParameterManipulationSequence(manipulations);

    }

    private static void addDistancedBoundary(Parameter birthBorder, double landscapeD, double releaseD, Parameter cabbageRectangle, Parameter landscapeBorder, List manipulations, boolean structureChanged) {
        BoundaryParameters.addDistancedBoundaryManipulation(birthBorder, landscapeD, releaseD, cabbageRectangle, landscapeBorder, manipulations, BoundaryShape.CIRCULAR, BoundaryShape.CIRCULAR, BoundaryShape.RECTANGULAR, structureChanged);
    }

    public static void addCabbageRadiusManipulation(ExperimentPlan plan) {
        List manipulations = new ArrayList();
        Parameter radius = plan.getParameterTemplate().findParameter(CabbageParameters.ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED_CABBAGE_RADIUS);

        manipulations.add(new ParameterManipulation(radius, 5d));
//        manipulations.add(new ParameterManipulation(radius, 10d));
//        manipulations.add(new ParameterManipulation(radius, 30d));

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addInterEdgeSeparationManipulation(ExperimentPlan plan) {
        List manipulations = new ArrayList();
        Parameter iterEdgeSeparation = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_INTER_EDGE_SEPARATION);

//        manipulations.add(new ParameterManipulation(iterEdgeSeparation, 0d));
//        manipulations.add(new ParameterManipulation(iterEdgeSeparation, 5d));
        manipulations.add(new ParameterManipulation(iterEdgeSeparation, 10d));
//        manipulations.add(new ParameterManipulation(iterEdgeSeparation, 20d));
//        manipulations.add(new ParameterManipulation(iterEdgeSeparation, 40d));
//        manipulations.add(new ParameterManipulation(iterEdgeSeparation, 80d));

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addAngleOfTurnManipulation(ExperimentPlan plan) {
        List manipulations = new ArrayList();
        Parameter angleOfTurn = plan.getParameterTemplate().findParameter(ButterflyParameters.BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK_ANGLE_OF_TURN);

//        manipulations.add(new ParameterManipulation(angleOfTurn, 0d));
//        manipulations.add(new ParameterManipulation(angleOfTurn, 10d));
        manipulations.add(new ParameterManipulation(angleOfTurn, 20d));
//        manipulations.add(new ParameterManipulation(angleOfTurn, 40d));
//        manipulations.add(new ParameterManipulation(angleOfTurn, 80d));

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addMoveLengthManipulation(ExperimentPlan plan) {
        List manipulations = new ArrayList();
        Parameter moveLength = plan.getParameterTemplate().findParameter(ButterflyParameters.MOVEMENT_BEHAVIOUR_RANDOMWALK_MOVE_LENGTH);

        manipulations.add(new ParameterManipulation(moveLength, 1L));
//        manipulations.add(new ParameterManipulation(moveLength, 5L));
//        manipulations.add(new ParameterManipulation(moveLength, 10L));
//        manipulations.add(new ParameterManipulation(moveLength, 20L));
//        manipulations.add(new ParameterManipulation(moveLength, 40L));
//        manipulations.add(new ParameterManipulation(moveLength, 80L));

        plan.addParameterManipulationSequence(manipulations);

    }


}
