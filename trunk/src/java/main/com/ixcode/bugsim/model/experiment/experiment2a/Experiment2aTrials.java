/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment2a;

import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.SimulationParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.List;

/**
 * Description : R=5,  L = 5, S = 10, A = 20, P = CORNER_CENTRE, B = {0, 5, 10, 20, 50, 100, 200}
 * <p/>
 * Revision: $Revision$
 */
public class Experiment2aTrials {

    //@todo remove need to define these...
    public static final String TRIAL_DEBUG = "TrDEBUG";
    public static final String TRIAL_A = "TrA";
    public static final String TRIAL_A1 = "TrA1";
    public static final String TRIAL_A2 = "TrA2";
    public static final String TRIAL_A3 = "TrA3";
    public static final String TRIAL_A4 = "TrA4";
    public static final String TRIAL_A5 = "TrA5";
    public static final String TRIAL_A6 = "TrA6";
    public static final String TRIAL_B = "TrB";
    public static final String TRIAL_B1 = "TrB1";
    public static final String TRIAL_C = "TrC";
    public static final String TRIAL_D = "TrD";
    public static final String TRIAL_E = "TrE";
    public static final String TRIAL_F1 = "TrF1";
    public static final String TRIAL_F2 = "TrF2";
    public static final String TRIAL_F3 = "TrF3";
    public static final String TRIAL_F4 = "TrF4";
    public static final String TRIAL_F5 = "TrF5";
    public static final String TRIAL_G = "TrG";
    public static final String TRIAL_H = "TrH";
    public static final String TRIAL_I = "TrI";
    public static final String TRIAL_J1 = "TrJ1";
    public static final String TRIAL_J2 = "TrJ2";
    public static final String TRIAL_K1 = "TrK1";
    public static final String TRIAL_K2 = "TrK2";
    public static final String TRIAL_K3 = "TrK3";
    public static final String TRIAL_K4 = "TrK4";
    public static final String TRIAL_K5 = "TrK5";
    public static final String TRIAL_L1 = "TrL1";
    public static final String TRIAL_L2 = "TrL2";
    public static final String TRIAL_L3 = "TrL3";
    public static final String TRIAL_L4 = "TrL4";


    public static final long REPS = 50;
    public static final long MAX_EGGS = 100000;
    public static final long POP_SIZE = 200;
    public static final long MAX_BFLIES = 5000;


    public static void addManipulationsTrialDEBUG(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_DEBUG);
        plan.setDescription("Used to try stuff out.");
        plan.setDescription("Simple layout to test out information surface");

        long replicants = 1;
        long maxEggs = 1;
        long populationSize = 1;


        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};

        double[] B = new double[]{0}; //800,

        boolean[] RFML = new boolean[]{false};//true,  // Goes with the B
        long[] butterflies = new long[]{1}; //4000


        Experiment2aTrials.configureSimulation(plan, maxEggs, populationSize, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), true);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);

    }


    public static void addManipulationsTrialA1(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_A1);
        plan.setDescription("Simple cabbage layout demonstrates Centre - Corner ratio is different when have information about the cabbages.");


        initialiseTrialA(plan, 100, 1, 100, 0);

    }

    public static void addManipulationsTrialA2(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_A2);
        plan.setDescription("Simple cabbage layout demonstrates Centre - Corner ratio is different when have information about the cabbages.");


        initialiseTrialA(plan, 100, 100, 1000, 0);

    }

    public static void addManipulationsTrialA3(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_A3);
        plan.setDescription("Simple cabbage layout demonstrates Centre - Corner ratio is different when have information about the cabbages.");


        initialiseTrialA(plan, 100, 1, 100, 0);

    }

    public static void addManipulationsTrialA4(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_A4);
        plan.setDescription("Simple cabbage layout demonstrates Centre - Corner ratio is different when have information about the cabbages.");


        initialiseTrialA(plan, 100, 100, 1000, 100);

    }

     public static void addManipulationsTrialA5(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_A5);
        plan.setDescription("Simple cabbage layout demonstrates Centre - Corner ratio is different when have information about the cabbages.");


        initialiseTrialA(plan, 100, 1, 100, 100);

    }

    public static void addManipulationsTrialA6(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_A6);
        plan.setDescription("Simple cabbage layout demonstrates Centre - Corner ratio is different when have information about the cabbages.");


        initialiseTrialA(plan, 1000, 10, 1000, 100);

    }

    private static void initialiseTrialA(ExperimentPlan plan, long maxEggs, long numberOfEggs,  long maxAge, long landscapeBorder) {
        long replicants = 10;

        long populationSize = 1;


        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{0};
        double[] L = new double[]{5};

        double[] B = new double[]{0}; //800,
        double[] LSCP = new double[] {landscapeBorder};
        long[] butterflies = new long[]{1000}; //4000

        Experiment2aTrials.configureSimulation(plan, maxEggs, populationSize, replicants);

        CabbageParameters.addSignalSurfaceNormalGaussian(plan.getParameterTemplate(), 1e7, 1e5);

        ButterflyParameters.setNumberOfEggs(plan.getParameterTemplate(), numberOfEggs);

        ButterflyParameters.setTwoSensorSignalMovementStrategy(plan.getParameterTemplate(), L[0], A[0], 5, 500, 1);
        ButterflyParameters.setLimitedAgeAndEggsMortalityStrategy(plan, maxAge);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, LSCP, butterflies, BoundaryShape.CIRCULAR);
    }


    public static void addManipulationsTrialB(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_B);
        plan.setDescription("Levin Part II 2006 - Field E - Varying L and A to see if it changes the shape of the distribution. B=300");
        String cabbageFilename = PredefinedResourceLayoutStrategy.DEFAULT_RESOURCE_LAYOUT_FILENAME;

        long replicants = 10;
        long maxEggs = 613; // same as field results
        long populationSize = 1;
        long maxButterflies = 100000;
        long numberOfEggs = 1;
        long maxAge = 1000;

        double[] A = new double[]{0}; //20
        double[] L = new double[]{150}; // 1.75m from field results
        double[] B = new double[]{300};


        configureTrialB(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename, numberOfEggs, maxAge, A, L, B);

    }

    public static void addManipulationsTrialB1(ExperimentPlan plan) {
        plan.setTrialName(Experiment2aTrials.TRIAL_B1);
        plan.setDescription("Sensory Perception - Levin Part II 2006 - Field E - Varying L and A to see if it changes the shape of the distribution. B=300");
        String cabbageFilename = PredefinedResourceLayoutStrategy.DEFAULT_RESOURCE_LAYOUT_FILENAME;

        long replicants = 10;
        long maxEggs = 613; // same as field results
        long populationSize = 1;
        long maxButterflies = 100000;
        long numberOfEggs = 1;
        long maxAge = 1000;

        double[] A = new double[]{0}; //20
        double[] L = new double[]{150}; // 1.75m from field results
        double[] B = new double[]{300};


        configureTrialB(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename, numberOfEggs, maxAge, A, L, B);

    }

    private static void configureTrialB(ExperimentPlan plan, long maxEggs, long populationSize, long maxButterflies, long replicants, String cabbageFilename, long numberOfEggs, long maxAge, double[] a, double[] l, double[] b) {
        Experiment2aTrials.configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename);

        CabbageParameters.addSignalSurfaceNormalGaussian(plan.getParameterTemplate(), 1e7, 1e5);

        ButterflyParameters.setNumberOfEggs(plan.getParameterTemplate(), numberOfEggs);

        ButterflyParameters.setTwoSensorSignalMovementStrategy(plan.getParameterTemplate(), l[0], a[0], 5, 500, 1);
        ButterflyParameters.setLimitedAgeAndEggsMortalityStrategy(plan, maxAge);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        ButterflyParameters.addAngleOfTurnManipulation(plan, a);
        ButterflyParameters.addMoveLengthManipulation(plan, l);
        SimulationParameters.addReleaseBoundaryManipulations(plan, b, BoundaryShape.CIRCULAR);
    }

    private static void configureSimulation(ExperimentPlan plan, long maxEggs, long populationSize, long maxButterflies, long replicants, String cabbageLayoutFilename) {
        double radius = 10d;
        ScaledDistance landscapeScale = new ScaledDistance(1, DistanceUnitRegistry.centimetres());
        List initialisationParams = CabbageParameters.loadCabbageInitialisationParameters(cabbageLayoutFilename, radius, DistanceUnitRegistry.metres(), landscapeScale);

        ScaledDistance dist = new ScaledDistance(36, DistanceUnitRegistry.metres());
        double dim = dist.convertToLogicalDistance(landscapeScale);


        CartesianBounds patchBounds = new CartesianBounds(0, 0, dim, dim);

        StrategyDefinitionParameter predefinedDef = CabbageParameters.createPredefinedLayoutCabbageFactoryStrategyP(initialisationParams, patchBounds);

        CabbageParameters.setCabbageFactoryStrategyP(plan.getParameterTemplate(), predefinedDef);

//        Parameter numberOfEggsP = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_EGGS);
//        numberOfEggsP.setValue(maxEggs);


        SimulationParameters.configureSimulationParameters(plan, replicants);

    }


    private static void configureSimulation(ExperimentPlan plan, long maxEggs, long populationSize, long maxButterflies, long replicants) {
        Parameter layoutType = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_TYPE);
        layoutType.setValue(CalculatedResourceLayoutType.CORNER_CENTRE);

//        Parameter numberOfEggsP = plan.getParameterTemplate().findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_EGGS);
//        numberOfEggsP.setValue(maxEggs);


        SimulationParameters.configureSimulationParameters(plan, replicants);

    }

    private static void configureSimulation(ExperimentPlan plan, long maxEggs, long populationSize, long replicants) {
        configureSimulation(plan, maxEggs, populationSize, 1, replicants);

    }

    private static final String INPUT_CABBAGE_FILENAME_TEST = "/Users/jim/Documents/Projects/msc/Simulation Input/Field E Levin II 2006 layout TEST.csv";

    private static final String INPUT_FILENAME_N_500 = "/Users/jim/Documents/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-500.csv";


}
