/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1b;

import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.SimulationParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterManipulation;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1bParameters {

    public static void addManipulations(ExperimentPlan plan) {
//        Parameter layoutType = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_TYPE);
//        layoutType.setValue(CalculatedCabbageLayoutType.CORNER_CENTRE);

//        Parameter numberOfEggs  = plan.getParameterTemplate().findParameter(SimulationParameters.SIMULATION_MAX_NUMBER_OF_EGGS);
//        numberOfEggs.setValue(new Long(1));

//        Parameter populationSize = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_POPULATION_SIZE);
//        populationSize.setValue(new Long(300));
//
//        Parameter numberOfButterflies = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_BUTTERFLIES);
//        numberOfButterflies.setValue(new Long(1000));

        Parameter outputHistory = plan.getParameterTemplate().findParameter(ButterflyParameters.BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY);
        outputHistory.setValue(new Boolean(false));

        addAgeManipulations(plan);


//        Parameter includeEscaped = plan.getParameterTemplate().findParameter(SimulationParameters.SIMULATION_INCLUDE_ESCAPED_AGENTS_IN_HISTORY);
//        includeEscaped.setValue(new Boolean(false));


//        addReleaseBoundaryManipulations(plan);
//        addInterEdgeSeparationManipulation(plan);
//        addCabbageRadiusManipulation(plan);
        addMoveLengthManipulation(plan);
        addAngleOfTurnManipulation(plan);
    }

    private static void addAgeManipulations(ExperimentPlan plan) {
        Parameter maxAge = plan.getParameterTemplate().findParameter(ButterflyParameters.LIFECYCLE_MORTALITY_LIMITED_AGE_MAX);

        List manipulations = new ArrayList();
        manipulations.add(new ParameterManipulation(maxAge, 1));
        manipulations.add(new ParameterManipulation(maxAge, 10));
        manipulations.add(new ParameterManipulation(maxAge, 50));
        manipulations.add(new ParameterManipulation(maxAge, 100));
        manipulations.add(new ParameterManipulation(maxAge, 500));





        plan.addParameterManipulationSequence(manipulations);

    }

    /**
     * Manipulates the release boundary and landscape sizes.
     *
     * @param plan
     */
    public static void addBoundaryManipulations(ExperimentPlan plan) {
//        Parameter cabbageRectangle = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_BOUNDS);
//        Parameter landscapeBorder = plan.getParameterTemplate().findParameter(LandscapeParameters.ENVIRONMENT_LANDSCAPE_BORDER_DEF);
//        Parameter birthBorder = plan.getParameterTemplate().findParameter(ButterflyParameters.BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF);

        List manipulations = new ArrayList();


        Parameter birth = plan.getParameterTemplate().findParameter(ButterflyParameters.P_LIFECYCLE_RELEASE);


        StrategyDefinitionParameter fixedLocation = ButterflyParameters.createBirthFromCentreOfLandscape(plan.getParameterTemplate());
        ParameterManipulation manipCentreRelease = new ParameterManipulation(birth, fixedLocation);
        manipulations.add(manipCentreRelease);

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

//        manipulations.add(new ParameterManipulation(moveLength, 1L));
//        manipulations.add(new ParameterManipulation(moveLength, 5L));
        manipulations.add(new ParameterManipulation(moveLength, 5L));
//        manipulations.add(new ParameterManipulation(moveLength, 20L));
//        manipulations.add(new ParameterManipulation(moveLength, 40L));
//        manipulations.add(new ParameterManipulation(moveLength, 80L));

        plan.addParameterManipulationSequence(manipulations);

    }

    public static void addManipulationsTrialDebug(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_DEBUG);


        long replicants = REPS;
        long populationSize = 500;
        long maxButterflies = 1000;


        plan.setDescription("Debug to check everything. Many butterflies(" + maxButterflies + ") to see the mean squared dispersal distance.");

        double[] A = new double[]{20};
        double[] L = new double[]{1};
        long[] MXAG = new long[]{1000};

        configureSimulation(plan, populationSize, maxButterflies, replicants);

        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        ButterflyParameters.addAgeManipulations(plan, MXAG);

    }

    /**
     * this way is too long so we are doing it with TrialB
     * @param plan
     */
    public static void addManipulationsTrialA(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_B);


        long replicants = REPS;
        long populationSize = POP_SIZE;
        long maxButterflies = MAX_BFLIES;


        plan.setDescription("Many butterflies(" + maxButterflies + ") to see the mean squared dispersal distance.");

        double[] A = new double[]{20};
        double[] L = new double[]{1, 1, 1, 1};
        long[] MXAG = new long[]{1, 2, 3, 4, 5, 6, 10, 20, 50, 100, 200, 400, 500, 700, 1000};

        configureSimulation(plan, populationSize, maxButterflies, replicants);

        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        ButterflyParameters.addAgeManipulations(plan, MXAG);


    }

    public static void addManipulationsTrialB(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_B);


        long replicants = REPS;
        long populationSize = POP_SIZE;
        long maxButterflies = MAX_BFLIES;


        plan.setDescription("Many butterflies(" + maxButterflies + ") to see the mean squared dispersal distance.");

        SimulationCategory.setArchiveRemovedAgents(plan.getParameterTemplate(), true);


        double[] A = new double[]{20};
        double[] L = new double[]{1, 1, 1, 1};
        long[] MXAG = new long[]{1000};

        configureSimulation(plan, populationSize, maxButterflies, replicants);

        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        ButterflyParameters.addAgeManipulations(plan, MXAG);


    }

    /**
     * Just in case we want to do something specific to this experiment
     *
     * @param plan
     * @param populationSize
     * @param maxButterflies
     * @param replicants
     */
    public static void configureSimulation(ExperimentPlan plan, long populationSize, long maxButterflies, long replicants) {
        SimulationParameters.configureSimulationParameters(plan, replicants);


    }


    public static final String TRIAL_DEBUG = "TrDEBUG";
    public static final String TRIAL_A = "TrA";
    public static final String TRIAL_B = "TrB";
    public static final String TRIAL_C = "TrC";
    public static final long REPS = 1;
    public static final long POP_SIZE = 200;
    public static final long MAX_BFLIES = 100000;
}
