/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1a;

import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.SimulationParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

import java.util.ArrayList;

/**
 * Description : R=5,  L = 5, S = 10, A = 20, P = CORNER_CENTRE, B = {0, 5, 10, 20, 50, 100, 200}
 * <p/>
 * Revision: $Revision$
 */
public class Experiment1aTrials {

    public static final String TRIAL_DEBUG = "TrDEBUG";
    public static final String TRIAL_A = "TrA";
    public static final String TRIAL_B = "TrB";
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
        plan.setTrialName(TRIAL_DEBUG);
        plan.setDescription("Used to try stuff out.");
        plan.setDescription("B=0 vs B=800 POPSIZE=1, 500 EGGs. move length L=80 A=20.");

        long replicants = 10;
        long maxEggs = 500;
        long populationSize = 1;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};

        double[] B = new double[]{0, 800}; //800,

        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{6000, 6000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), true);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);

    }


    public static void addManipulationsTrialA(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_A);
        plan.setDescription("Four lots of L=1 to see the variation in egg ratio as the border distance increases (b)");

        long replicants = REPS;
        long maxEggs = MAX_EGGS;
        long populationSize = POP_SIZE;
        long maxButterflies = MAX_BFLIES;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{1, 1, 1, 1};
        double[] B = new double[]{0, 5, 10, 20, 100};


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);
    }

    public static void addManipulationsTrialB(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_B);
        plan.setDescription("Varying L to see if egg ratio changes with B (release distance).");

        long replicants = 20;
        long maxEggs = 100000;
        long populationSize = 200;
        long maxButterflies = 10000;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{1, 2, 3, 4, 5, 20, 80};
//        double[] L = new double[]{80};
        double[] B = new double[]{0, 5, 10, 20, 80, 100, 400, 800};


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);
    }

    public static void addManipulationsTrialC(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_C);
        plan.setDescription("Varying Angle of turn to see if egg ratio changes with B (release distance).");


        long replicants = REPS;
        long maxEggs = MAX_EGGS;
        long populationSize = POP_SIZE;
        long maxButterflies = MAX_BFLIES;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{0, 10, 20, 40, 80};
        double[] L = new double[]{1};
        double[] B = new double[]{0, 5, 10, 20, 100};


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);
    }

    public static void addManipulationsTrialD(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_D);
        plan.setDescription("Changing Step Length - Recording angle of turn and distance as the butterflies cross the zero boundary.");

        long replicants = 1;
        long maxEggs = 1000;
        long populationSize = 200;
        long maxButterflies = 10000;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{5, 10, 40, 60, 80};
        double[] B = new double[]{5, 20, 80, 100, 200};


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);
    }

    public static void addManipulationsTrialE(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_E);
        plan.setDescription("Changing Angle of turn - Recording angle of turn and distance as the butterflies cross the zero boundary.");

        long replicants = 1;
        long maxEggs = 1000;
        long populationSize = 200;
        long maxButterflies = 10000;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{5, 10, 20, 50, 90};
        double[] L = new double[]{80};
        double[] B = new double[]{5, 20, 80, 100, 200};


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);
    }

    public static void addManipulationsTrialF1(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_F1);
        plan.setDescription("B=0 vs B=800 L=80 A=20.");

        long replicants = 50;
        long maxEggs = 10000;
        long populationSize = 200;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};

        double[] B = new double[]{0, 800};
        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{400, 4000};


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);


    }


    public static void addManipulationsTrialF2(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_F2);
        plan.setDescription("B=800 vs B=801 should produce the same results. move length L=80 A=20.");

        long replicants = 20;
        long maxEggs = 10000;
        long populationSize = 200;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};


        double[] B = new double[]{800, 801}; //800,
        double[] LB = new double[]{0, 0}; // landscape distance from boundary

        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{4000, 4000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, LB, RFML, butterflies, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialF3(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_F3);
        plan.setDescription("B=0 vs B=800 POPSIZE=1, 50 EGGs. move length L=80 A=20.");

        long replicants = 50;
        long maxEggs = 50;
        long populationSize = 1;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};

        double[] B = new double[]{0, 800}; //800,

        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{6000, 6000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialF4(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_F4);
        plan.setDescription("B=0 vs B=800 POP=1, 500 EGGs. move length L=80 A=20.");

        long replicants = 50;
        long maxEggs = 500;
        long populationSize = 1;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};

        double[] B = new double[]{0, 800}; //800,

        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{100000, 100000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);
    }

    public static void addManipulationsTrialG(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_G);
        plan.setDescription("Using pre recorded boundary crossings to initialise the first step length, heading and position around the boundary.");

        long replicants = 1;
        long maxEggs = 1000;
        long populationSize = 10;
        long maxButterflies = 2100;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{80};

        double[] B = new double[]{0}; //800
        String filename = "/Users/jim/Documents/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-2151.csv";

        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), true);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        ButterflyParameters.setBirthPredefinedStrategy(plan, filename);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialH(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_H);
        plan.setDescription("Line of sight from release boundary. Max Age of 1 move - generate starting points systematically from 0 through 90 degrees around the border first move length is M and into the centre we need to record wether this butterfly hit a centre - corner or missed a cabbage.");

        long replicants = 1;
        long maxEggs = 10000;
        long populationSize = 10;
        long maxButterflies = 2100;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{80};
        double[] M = new double[]{1, 10, 20, 40, 80, 120};

        double[] B = new double[]{0}; //800

        long[] AGES = new long[]{1};
        String filename = "/Users/jim/Documents/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-2151.csv";

        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);


        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), false);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), false);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);
        ButterflyParameters.setLimitedAgeMortalityStrategy(plan, 1);

        // the boundary manipulation requires this.
        ButterflyParameters.setBirthPredefinedStrategy(plan, new ArrayList());
        ButterflyParameters.addPredefinedBirthManipulation(plan, 0, 90, 1, 1, true, M);



        SimulationCategory.setArchiveRemovedAgents(plan.getParameterTemplate(), true);
        SimulationCategory.setIncludeEscapedAgentsInHistory(plan.getParameterTemplate(), true);


        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialI(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_I);
        plan.setDescription("Line of sight from release boundary. no max age - just normal lay egg - generate starting points systematically from 0 through 90 degrees around the border first move length is same as L and into the centre we need to record wether this butterfly hit a centre - corner or missed a cabbage. Then moves randomly as per usual");

        long replicants = 5;
        long maxEggs = 10000;
        long populationSize = 10;
        long maxButterflies = 2100;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{1, 10, 20, 40, 80, 120};
//        double[] M = new double[]{1, 10, 20, 40, 80, 120};
        double[] M = new double[]{-1}; // uses L instead

        double[] B = new double[]{0}; //800

        long[] AGES = new long[]{1};
        String filename = "/Users/jim/Documents/Projects/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-2151.csv";

        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);


        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), false);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), true);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);
//        ButterflyParameters.setLimitedAgeMortalityStrategy(plan, 1);

        ButterflyParameters.setBirthPredefinedStrategy(plan, filename);
        // the boundary manipulation requires this.
//        ButterflyParameters.setBirthPredefinedStrategy(plan, new ArrayList());
//        ButterflyParameters.addPredefinedBirthManipulation(plan, 0, 90, 1, 1, true, M);



        SimulationCategory.setArchiveRemovedAgents(plan.getParameterTemplate(), true);
        SimulationCategory.setIncludeEscapedAgentsInHistory(plan.getParameterTemplate(), true);


        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialJ1(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_J1);
        plan.setDescription("Random walkers release from the boundary initialised from the recorded buterflies at B=800 I is generated rather than determistic");

        long replicants = 50;
        long maxEggs = 10000;
        long populationSize = 10;
        long maxButterflies = 2100;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{1, 10, 20, 40, 80, 120};
//        double[] M = new double[]{1, 10, 20, 40, 80, 120};
        double[] M = new double[]{-1}; // uses L instead

        double[] B = new double[]{0}; //800

        long[] AGES = new long[]{1};
        String filename = "/Users/jim/Documents/Projects/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-2151.csv";

        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);


        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), false);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), true);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);
//        ButterflyParameters.setLimitedAgeMortalityStrategy(plan, 1);

        ButterflyParameters.setBirthPredefinedStrategy(plan, filename);
        // the boundary manipulation requires this.
//        ButterflyParameters.setBirthPredefinedStrategy(plan, new ArrayList());
//        ButterflyParameters.addPredefinedBirthManipulation(plan, 0, 90, 1, 1, true, M);


        SimulationCategory.setArchiveRemovedAgents(plan.getParameterTemplate(), true);
        SimulationCategory.setIncludeEscapedAgentsInHistory(plan.getParameterTemplate(), true);


        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialJ2(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_J1);
        plan.setDescription("Real Random walkers released from zero boundary with RFML to compare to the pre recorded ones the location is randomly rather than deterministically generated");

        long replicants = 5;
        long maxEggs = 10000;
        long populationSize = 10;
        long maxButterflies = 2100;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20}; //20
        double[] L = new double[]{1, 10, 20, 40, 80, 120};
//        double[] M = new double[]{1, 10, 20, 40, 80, 120};
        double[] M = new double[]{-1}; // uses L instead

        double[] B = new double[]{0}; //800

        long[] AGES = new long[]{1};
        String filename = "/Users/jim/Documents/Projects/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-2151.csv";

        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);


        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), false);

        ButterflyParameters.setRandomFirstMoveLength(plan.getParameterTemplate(), true);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);
//        ButterflyParameters.setLimitedAgeMortalityStrategy(plan, 1);

        ButterflyParameters.setBirthPredefinedStrategy(plan, filename);
        // the boundary manipulation requires this.
//        ButterflyParameters.setBirthPredefinedStrategy(plan, new ArrayList());
//        ButterflyParameters.addPredefinedBirthManipulation(plan, 0, 90, 1, 1, true, M);



        SimulationCategory.setArchiveRemovedAgents(plan.getParameterTemplate(), true);
        SimulationCategory.setIncludeEscapedAgentsInHistory(plan.getParameterTemplate(), true);


        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);
        SimulationParameters.addReleaseBoundaryManipulations(plan, B, BoundaryShape.CIRCULAR);


    }


    public static void addManipulationsTrialK1(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_K1);
        plan.setDescription("B=0 RANDOM ZONE vs B=800 -  L=80 A=20.");

        long replicants = 50;//30
        long maxEggs = 10000;
        long populationSize = 200;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};


        StrategyDefinitionParameter RANDOM_IN_ZONE = ButterflyParameters.createReleaseRandomInZoneAroundCabbagePatchStrategy(plan.getParameterTemplate(), 80);
        StrategyDefinitionParameter RANDOM_AROUND_BORDER = ButterflyParameters.createReleaseRandomAroundCabbagePatchStrategy(plan.getParameterTemplate());
        StrategyDefinitionParameter[] releaseStrategies = new StrategyDefinitionParameter[]{RANDOM_IN_ZONE, RANDOM_AROUND_BORDER};
        double[] B = new double[]{0, 800}; //800,
        double[] LB = new double[]{80, 0}; // landscape distance
        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{1000, 4000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);

        SimulationParameters.addReleaseBoundaryManipulations(plan, B, LB, RFML, butterflies, releaseStrategies, BoundaryShape.CIRCULAR);


    }


    public static void addManipulationsTrialK2(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_K2);
        plan.setDescription("Test of the manipulation of release strategy (No Random ZONE):B=0 RFML=FALSE vs B=800 RFML=FALSE -  L=80 A=20.");

        long replicants = 50;//20
        long maxEggs = 10000;
        long populationSize = 200;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};


        StrategyDefinitionParameter RANDOM_AROUND_BORDER = ButterflyParameters.createReleaseRandomAroundCabbagePatchStrategy(plan.getParameterTemplate());
        StrategyDefinitionParameter[] releaseStrategies = new StrategyDefinitionParameter[]{RANDOM_AROUND_BORDER, RANDOM_AROUND_BORDER};
        double[] B = new double[]{0, 800}; //800,
        double[] LB = new double[]{0, 0}; // landscape distance
        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{500, 4000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);

        SimulationParameters.addReleaseBoundaryManipulations(plan, B, LB, RFML, butterflies, releaseStrategies, BoundaryShape.CIRCULAR);


    }

    public static void addManipulationsTrialK3(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_K3);
        plan.setDescription("B=0 RANDOM ZONE vs B=800 POP=1 50 eggs. L=80 A=20.");

        long replicants = 50;//30
        long maxEggs = 50;
        long populationSize = 1;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};


        StrategyDefinitionParameter RANDOM_IN_ZONE = ButterflyParameters.createReleaseRandomInZoneAroundCabbagePatchStrategy(plan.getParameterTemplate(), 80);
        StrategyDefinitionParameter RANDOM_AROUND_BORDER = ButterflyParameters.createReleaseRandomAroundCabbagePatchStrategy(plan.getParameterTemplate());
        StrategyDefinitionParameter[] releaseStrategies = new StrategyDefinitionParameter[]{RANDOM_IN_ZONE, RANDOM_AROUND_BORDER};
        double[] B = new double[]{0, 800}; //800,
        double[] LB = new double[]{80, 0}; // landscape distance
        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{1000, 4000}; //4000


        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);

        SimulationParameters.addReleaseBoundaryManipulations(plan, B, LB, RFML, butterflies, releaseStrategies, BoundaryShape.CIRCULAR);


    }


    public static void addManipulationsTrialK4(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_K4);
        plan.setDescription("B=0 (RANDOM ZONE) vs B=800 500 Eggs POPSIZE=1 -  L=80 A=20.");

        long replicants = 50;
        long maxEggs = 500;  //500
        long populationSize = 1;
        long maxButterflies = 200;

        double[] S = new double[]{10};
        double[] R = new double[]{5};
        double[] A = new double[]{20};
        double[] L = new double[]{80};


        StrategyDefinitionParameter RANDOM_IN_ZONE = ButterflyParameters.createReleaseRandomInZoneAroundCabbagePatchStrategy(plan.getParameterTemplate(), 80);
        StrategyDefinitionParameter RANDOM_AROUND_BORDER = ButterflyParameters.createReleaseRandomAroundCabbagePatchStrategy(plan.getParameterTemplate());
        StrategyDefinitionParameter[] releaseStrategies = new StrategyDefinitionParameter[]{RANDOM_IN_ZONE, RANDOM_AROUND_BORDER};
        double[] B = new double[]{0, 800}; //800,
        double[] LB = new double[]{80, 0}; // landscape distance
        boolean[] RFML = new boolean[]{false, false};//true,  // Goes with the B
        long[] butterflies = new long[]{100000, 100000}; //4000

        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

        SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
        SimulationParameters.addCabbageRadiusManipulation(plan, R);
        ButterflyParameters.addAngleOfTurnManipulation(plan, A);
        ButterflyParameters.addMoveLengthManipulation(plan, L);

        SimulationParameters.addReleaseBoundaryManipulations(plan, B, LB, RFML, butterflies, releaseStrategies, BoundaryShape.CIRCULAR);


    }


    public static void addManipulationsTrialL1(ExperimentPlan plan) {
            plan.setTrialName(TRIAL_L1);
            plan.setDescription("B=0 RFML=TRUE vs B=800 POP=1 L=80 A=20.");

            long replicants = 50;
            long maxEggs = 50;
            long populationSize = 1;
            long maxButterflies = 200;

            double[] S = new double[]{10};
            double[] R = new double[]{5};
            double[] A = new double[]{20};
            double[] L = new double[]{80};

            double[] B = new double[]{0, 800};
            boolean[] RFML = new boolean[]{true, false};//true,  // Goes with the B
            long[] butterflies = new long[]{6000, 6000};


            configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

            SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

            ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

            SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
            SimulationParameters.addCabbageRadiusManipulation(plan, R);
            ButterflyParameters.addAngleOfTurnManipulation(plan, A);
            ButterflyParameters.addMoveLengthManipulation(plan, L);
            SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);


        }

//        double[] B = new double[]{0, 10, 80, 100, 800, 1000, 1500, 2000}; //800,
//        boolean[] RFML = new boolean[]{false, false, false, false, false, false, false, false};//true,  // Goes with the B
//        long[] butterflies = new long[]{1000, 1000, 1500, 2000, 4000, 8000, 10000, 20000};

    public static void addManipulationsTrialL4(ExperimentPlan plan) {
                plan.setTrialName(TRIAL_L4);
                plan.setDescription("B=0 RFML=TRUE vs B=800 POP=1 EGGS=500 L=80 A=20.");

                long replicants = 50;
                long maxEggs = 500;
                long populationSize = 1;
                long maxButterflies = 200;

                double[] S = new double[]{10};
                double[] R = new double[]{5};
                double[] A = new double[]{20};
                double[] L = new double[]{80};

                double[] B = new double[]{0, 800};
                boolean[] RFML = new boolean[]{true, false};//true,  // Goes with the B
                long[] butterflies = new long[]{100000, 100000};


                configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants);

                SimulationCategory.setRecordBoundaryCrossings(plan.getParameterTemplate(), true);

                ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

                SimulationParameters.addInterEdgeSeparationManipulation(plan, S);
                SimulationParameters.addCabbageRadiusManipulation(plan, R);
                ButterflyParameters.addAngleOfTurnManipulation(plan, A);
                ButterflyParameters.addMoveLengthManipulation(plan, L);
                SimulationParameters.addReleaseBoundaryManipulations(plan, B, RFML, butterflies, BoundaryShape.CIRCULAR);


            }

    private static void configureSimulation(ExperimentPlan plan, long maxEggs, long populationSize, long maxButterflies, long replicants) {
        Parameter layoutType = plan.getParameterTemplate().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_TYPE);
        layoutType.setValue(CalculatedResourceLayoutType.CORNER_CENTRE);



        SimulationParameters.configureSimulationParameters(plan, replicants);

    }


    private static final String INPUT_FILENAME_N_500 = "/Users/jim/Documents/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-500.csv";

}
