/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1c;

import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.SimulationParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.List;

/**
 * Description : R=5,  L = 5, S = 10, A = 20, P = CORNER_CENTRE, B = {0, 5, 10, 20, 50, 100, 200}
 * <p/>
 * Revision: $Revision$
 */
public class Experiment1cTrials {

    public static final String TRIAL_DEBUG = "TrDEBUG";
    public static final String TRIAL_A = "TrA";
    public static final String TRIAL_B = "TrB";
    public static final String TRIAL_C = "TrC";
    public static final String TRIAL_D = "TrD";
    public static final String TRIAL_E = "TrE";
    public static final String TRIAL_F = "TrF";
    public static final String TRIAL_G = "TrG";
    public static final String TRIAL_H = "TrH";
    public static final String TRIAL_I = "TrI";
    public static final String TRIAL_J1 = "TrJ1";
    public static final String TRIAL_J2 = "TrJ2";


    public static final long REPS = 50;
    public static final long MAX_EGGS = 100000;
    public static final long POP_SIZE = 200;
    public static final long MAX_BFLIES = 5000;


    public static void addManipulationsTrialDebug(ExperimentPlan plan) {
        plan.setTrialName(Experiment1cTrials.TRIAL_DEBUG);
        plan.setDescription("Used to try stuff out.");
        String cabbageFilename = Experiment1cTrials.INPUT_CABBAGE_FILENAME;

        long replicants = 1;
        long maxEggs = 1000;
        long populationSize = 1;
        long maxButterflies = 1;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{100};
//        double[] B = new double[]{0, 5, 10, 20, 40, 80, 100};
        double[] B = new double[]{0}; //800


//        double[] betadist= new double[]{1, 1.1, 1.2, 1.3, 1.4,1.5, 1.6,1.7, 1.8, 2};
//        double[] betadist= new double[]{0.5, 1, 2, 3, 4, 5};
//        boolean[] rfml = new boolean[] {false, true, true, true, true, true};


        Experiment1cTrials.configureSimulationParameters(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename);

//        SimulationParameters.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

//        ButterflyParameters.setBirthPredefinedStrategy(plan, cabbageFilename);

//        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
//        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);


    }


    public static void addManipulationsTrialA(ExperimentPlan plan) {
        plan.setTrialName(Experiment1cTrials.TRIAL_A);
        plan.setDescription("Experimental setup from Levin Part II 2006 - Field E - using L=100 A=20.");
        String cabbageFilename = Experiment1cTrials.INPUT_CABBAGE_FILENAME;

        long replicants = 1;
        long maxEggs = 1000;
        long populationSize = 200;
        long maxButterflies = 10000;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{100};
//        double[] B = new double[]{0, 5, 10, 20, 40, 80, 100};
        double[] B = new double[]{0}; //800

//        double[] betadist= new double[]{1, 1.1, 1.2, 1.3, 1.4,1.5, 1.6,1.7, 1.8, 2};
//        double[] betadist= new double[]{0.5, 1, 2, 3, 4, 5};
//        boolean[] rfml = new boolean[] {false, true, true, true, true, true};


        Experiment1cTrials.configureSimulationParameters(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename);

//        SimulationParameters.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

//        ButterflyParameters.setBirthPredefinedStrategy(plan, cabbageFilename);

//        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
//        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);

    }

    public static void addManipulationsTrialB(ExperimentPlan plan) {
        plan.setTrialName(Experiment1cTrials.TRIAL_B);
        plan.setDescription("Experimental setup from Levin Part II 2006 - Field E - Varying L and A to see if it changes the shape of the distribution. B=0");
        String cabbageFilename = Experiment1cTrials.INPUT_CABBAGE_FILENAME;

        long replicants = 4;
        long maxEggs = 1000;
        long populationSize = 150;
        long maxButterflies = 10000;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20, 60, 100}; //20
        double[] L = new double[]{50, 100, 150, 200};
//        double[] B = new double[]{0, 5, 10, 20, 40, 80, 100};
        double[] B = new double[]{0}; //800

//        double[] betadist= new double[]{1, 1.1, 1.2, 1.3, 1.4,1.5, 1.6,1.7, 1.8, 2};
//        double[] betadist= new double[]{0.5, 1, 2, 3, 4, 5};
//        boolean[] rfml = new boolean[] {false, true, true, true, true, true};


        Experiment1cTrials.configureSimulationParameters(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename);

//        SimulationParameters.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

//        ButterflyParameters.setBirthPredefinedStrategy(plan, cabbageFilename);

//        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
//        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);

    }

   public static void addManipulationsTrialC(ExperimentPlan plan) {
        plan.setTrialName(Experiment1cTrials.TRIAL_C);
            plan.setDescription("Increased replicants for accuracy. Increased release distance incase there is a bias.Experimental setup from Levin Part II 2006 - Field E - Varying L and A to see if it changes the shape of the distribution. B=300");
            String cabbageFilename = Experiment1cTrials.INPUT_CABBAGE_FILENAME;

            long replicants = 10;
            long maxEggs = 1000;
            long populationSize = 150;
            long maxButterflies = 12000;

            double[] S = new double[]{10};
            double[] R = new double[]{5};
//            double[] A = new double[]{20, 60, 100}; //20
//            double[] L = new double[]{50, 100, 150, 200};
            double[] A = new double[]{100}; //20   //Just to replicate the last iteration as it crashed on this one.
            double[] L = new double[]{200};
//        double[] B = new double[]{0, 5, 10, 20, 40, 80, 100};
            double[] B = new double[]{300}; //800

//        double[] betadist= new double[]{1, 1.1, 1.2, 1.3, 1.4,1.5, 1.6,1.7, 1.8, 2};
//        double[] betadist= new double[]{0.5, 1, 2, 3, 4, 5};
//        boolean[] rfml = new boolean[] {false, true, true, true, true, true};


            Experiment1cTrials.configureSimulationParameters(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename);

//        SimulationParameters.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

            ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
            ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

//        ButterflyParameters.setBirthPredefinedStrategy(plan, cabbageFilename);

//        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
//        SimulationParameters.addCabbageRadiusManipulation(plan, R);
            ButterflyParameters.addAngleOfTurnManipulation(plan, A);
            ButterflyParameters.addMoveLengthManipulation(plan, L);
            SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);

    }

    public static void addManipulationsTrialD(ExperimentPlan plan) {
        plan.setTrialName(Experiment1cTrials.TRIAL_D);
               plan.setDescription("Repeated experiment but with parameters from Byers.Experimental setup from Levin Part II 2006 - Field E - Varying L and A to see if it changes the shape of the distribution. B=300");
               String cabbageFilename = Experiment1cTrials.INPUT_CABBAGE_FILENAME;

               long replicants = 10;
               long maxEggs = 1000;
               long populationSize = 150;
               long maxButterflies = 10000;

               double[] S = new double[]{10};
               double[] R = new double[]{5};
                double[] A = new double[]{20, 50, 100}; //20
                double[] L = new double[]{25, 50, 150, 250};

//        double[] B = new double[]{0, 5, 10, 20, 40, 80, 100};
               double[] B = new double[]{50}; //800

//        double[] betadist= new double[]{1, 1.1, 1.2, 1.3, 1.4,1.5, 1.6,1.7, 1.8, 2};
//        double[] betadist= new double[]{0.5, 1, 2, 3, 4, 5};
//        boolean[] rfml = new boolean[] {false, true, true, true, true, true};


               Experiment1cTrials.configureSimulationParameters(plan, maxEggs, populationSize, maxButterflies, replicants, cabbageFilename);

//        SimulationParameters.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

               ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
               ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

//        ButterflyParameters.setBirthPredefinedStrategy(plan, cabbageFilename);

//        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
//        SimulationParameters.addCabbageRadiusManipulation(plan, R);
               ButterflyParameters.addAngleOfTurnManipulation(plan, A);
               ButterflyParameters.addMoveLengthManipulation(plan, L);
               SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);

    }


    private static void configureSimulationParameters(ExperimentPlan plan, long maxEggs, long populationSize, long maxButterflies, long replicants, String cabbageLayoutFilename) {
        double radius = 10d;
        ScaledDistance landscapeScale = new ScaledDistance(1, DistanceUnitRegistry.centimetres());
        List initialisationParams = CabbageParameters.loadCabbageInitialisationParameters(cabbageLayoutFilename, radius, DistanceUnitRegistry.metres(), landscapeScale);

        ScaledDistance dist = new ScaledDistance(36, DistanceUnitRegistry.metres());
        double dim = dist.convertToLogicalDistance(landscapeScale);


        CartesianBounds patchBounds = new CartesianBounds(0, 0, dim, dim);

        StrategyDefinitionParameter predefinedDef = CabbageParameters.createPredefinedLayoutCabbageFactoryStrategyP(initialisationParams, patchBounds);

        CabbageParameters.setCabbageFactoryStrategyP(plan.getParameterTemplate(), predefinedDef);



        SimulationParameters.configureSimulationParameters(plan, replicants);

    }


    private static final String INPUT_CABBAGE_FILENAME = getCabbageFilename();

    private static String getCabbageFilename() {
        String defaultFilename= "/Users/jim/Documents/Projects/msc/Simulation Input/Field E Levin II 2006 layout.csv";
        return System.getProperty("bugsim.exp1c.inputCabbageFilename", defaultFilename);
    }
}
