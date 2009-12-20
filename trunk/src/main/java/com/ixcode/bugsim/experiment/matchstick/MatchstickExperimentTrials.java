/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.matchstick;

import com.ixcode.bugsim.experiment.parameter.MatchstickParameters;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;

/**
 * Description : R=5,  L = 5, S = 10, A = 20, P = CORNER_CENTRE, B = {0, 5, 10, 20, 50, 100, 200}
 * <p/>
 * Revision: $Revision$
 */
public class MatchstickExperimentTrials {

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
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_DEBUG);
        plan.setDescription("Used to try stuff out.");

        long replicates = 1;

        configureSimulation(plan, replicates,40d);
    }


    public static void addManipulationsTrialA(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_A);
        plan.setDescription("Vary dropping distance - keep L=40");

        double D[] = new double[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
//        D = new double[] {D[8], D[10], D[11], D[12]};// Just 1 iteration


        long replicates = 1;

        configureSimulation(plan, replicates, 40d);

        MatchstickParameters.addDroppingDistanceManipulations(plan, D);


    }

    public static void addManipulationsTrialB(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_B);
        plan.setDescription("Varying dropping distance with Circular boundary");

        long replicates = 1;
        configureSimulation(plan, replicates,40d);

        double D[] = new double[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
//        D = new double[] {D[8], D[10], D[11], D[12]};// Just 1 iteration
        BoundaryShape SHAPES[] = new BoundaryShape[]{BoundaryShape.CIRCULAR};
        CartesianDimensions DIMENSIONS[] = new CartesianDimensions[]{new CartesianDimensions(50d)};
        Parameter LOCATION_1 = LandscapeParameters.createLandscapeCentredLocation(plan.getParameterTemplate(), -50d, 0);

        String[] NAMES = new String[]{IX_BOUNDARY_NAME};
        Parameter LOCATIONS[] = new Parameter[]{LOCATION_1};


        MatchstickParameters.addDroppingDistanceManipulations(plan, D);
        Parameter matchstickBoundaryP = MatchstickParameters.getIntersectionBoundaryP(plan.getParameterTemplate());
        BoundaryParameters.addBoundaryManipulations(plan, matchstickBoundaryP, NAMES, SHAPES, DIMENSIONS, LOCATIONS);

    }

    public static void addManipulationsTrialC(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_C);
        plan.setDescription("Circular boundar with same dimensions as in simulation");


        long replicates = 1;
        configureSimulation(plan, replicates, 160d);

        double D[] = DoubleMath.createSequenceDouble(80, 0, -1);
//        D = new double[] {D[8], D[10], D[11], D[12]};// Just 1 iteration
        BoundaryShape SHAPES[] = new BoundaryShape[]{BoundaryShape.CIRCULAR};
        CartesianDimensions DIMENSIONS[] = new CartesianDimensions[]{new CartesianDimensions(56d)};
        Parameter LOCATION_1 = LandscapeParameters.createLandscapeCentredLocation(plan.getParameterTemplate(), -56d, 0);

        String[] NAMES = new String[]{IX_BOUNDARY_NAME};
        Parameter LOCATIONS[] = new Parameter[]{LOCATION_1};


        MatchstickParameters.addDroppingDistanceManipulations(plan, D);
        Parameter matchstickBoundaryP = MatchstickParameters.getIntersectionBoundaryP(plan.getParameterTemplate());
        BoundaryParameters.addBoundaryManipulations(plan, matchstickBoundaryP, NAMES, SHAPES, DIMENSIONS, LOCATIONS);

    }

    public static void addManipulationsTrialD(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_D);
        plan.setDescription("Random matchsticks released within a release 'zone' of 1 step length around the border.");


        long replicates = 100;
        configureSimulation(plan, replicates, 80d);

        BoundaryShape SHAPES[] = new BoundaryShape[]{BoundaryShape.CIRCULAR};
        CartesianDimensions DIMENSIONS[] = new CartesianDimensions[]{new CartesianDimensions(56d)};
        Parameter LOCATION_1 = LandscapeParameters.createLandscapeCentredLocation(plan.getParameterTemplate(), 0, 0);

        String[] NAMES = new String[]{IX_BOUNDARY_NAME};
        Parameter LOCATIONS[] = new Parameter[]{LOCATION_1};

        BoundaryShape OUTER_SHAPES[] = new BoundaryShape[]{BoundaryShape.CIRCULAR};
        CartesianDimensions OUTER_DIMENSIONS[] = new CartesianDimensions[]{new CartesianDimensions(56d+80)};
        String[] OUTER_NAMES = new String[]{OUTER_BOUNDARY_NAME};

        long[] POPULATION_SIZES = new long[] {1000};

//        CartesianDimensions LANDSCAPE_SIZES = new CartesianDimension[] {new CartesianDimension()
//        LandscapeParameters.addLandscapeSizeManipulations()

        Parameter matchstickBoundaryP = MatchstickParameters.getIntersectionBoundaryP(plan.getParameterTemplate());
        BoundaryParameters.addBoundaryManipulations(plan, matchstickBoundaryP, NAMES, SHAPES, DIMENSIONS, LOCATIONS);

        MatchstickParameters.addRandomMatchstickCreationManipulations(plan, POPULATION_SIZES, OUTER_NAMES, OUTER_SHAPES, OUTER_DIMENSIONS, LOCATIONS);
    }

    public static void addManipulationsTrialE(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_E);
        plan.setDescription("Changing Angle of turn - Recording angle of turn and distance as the butterflies cross the zero boundary.");

        long replicants = 1;

    }

    public static void addManipulationsTrialF(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_F);
        plan.setDescription("Compare initial move for Zero and Random First Move Length against B=800 no random first move length L=80 A=20.");

        long replicants = 20;


    }


    public static void addManipulationsTrialG(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_G);
        plan.setDescription("Using pre recorded boundary crossings to initialise the first step length, heading and position around the boundary.");

        long replicants = 1;


    }

    public static void addManipulationsTrialH(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_H);
        plan.setDescription("Line of sight from release boundary. Max Age of 1 move - generate starting points systematically from 0 through 90 degrees around the border first move length is M and into the centre we need to record wether this butterfly hit a centre - corner or missed a cabbage.");

        long replicants = 1;

    }

    public static void addManipulationsTrialI(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_I);
        plan.setDescription("Line of sight from release boundary. no max age - just normal lay egg - generate starting points systematically from 0 through 90 degrees around the border first move length is same as L and into the centre we need to record wether this butterfly hit a centre - corner or missed a cabbage. Then moves randomly as per usual");

        long replicants = 5;


    }

    public static void addManipulationsTrialJ1(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_J1);
        plan.setDescription("Random walkers release from the boundary initialised from the recorded buterflies at B=800 I is generated rather than determistic");

        long replicants = 50;


    }

    public static void addManipulationsTrialJ2(ExperimentPlan plan) {
        plan.setTrialName(MatchstickExperimentTrials.TRIAL_J1);
        plan.setDescription("Real Random walkers released from zero boundary with RFML to compare to the pre recorded ones the location is randomly rather than deterministically generated");

        long replicants = 5;

    }

    private static void configureSimulation(ExperimentPlan plan, long replicates, double matchstickLength) {


        SimulationCategory.setNumberOfReplicates(plan.getParameterTemplate(), replicates);
        MatchstickParameters.addMatchstickParameters(plan.getParameterTemplate(), IX_BOUNDARY_NAME, matchstickLength);

    }


    private static final String INPUT_FILENAME_N_500 = "/Users/jim/Documents/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-500.csv";
    private static final String IX_BOUNDARY_NAME = "intersectionBoundary";
    private static final String OUTER_BOUNDARY_NAME = "outerBoundary";
}
