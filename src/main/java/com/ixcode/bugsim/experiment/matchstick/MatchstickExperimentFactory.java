/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.matchstick;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.experiment.parameter.EnvironmentParameters;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.IExperimentFactory;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.simulation.model.Simulation;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickExperimentFactory implements IExperimentFactory {



    public IExperiment createExperiment(String trialName) {


        Simulation simulation = new Simulation();
        ExperimentPlan plan = MatchstickExperimentFactory.createDefaultPlan(MatchstickExperimentFactory.EXP_1D_NAME, new CartesianDimensions(250));

        if (trialName.equals(MatchstickExperimentTrials.TRIAL_DEBUG)) {
            MatchstickExperimentTrials.addManipulationsTrialDebug(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_A)) {
            MatchstickExperimentTrials.addManipulationsTrialA(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_B)) {
            MatchstickExperimentTrials.addManipulationsTrialB(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_C)) {
            MatchstickExperimentTrials.addManipulationsTrialC(plan);
        } else  if (trialName.equals(MatchstickExperimentTrials.TRIAL_D)) {
            MatchstickExperimentTrials.addManipulationsTrialD(plan);
        } else  if (trialName.equals(MatchstickExperimentTrials.TRIAL_E)) {
            MatchstickExperimentTrials.addManipulationsTrialE(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_F)) {
            MatchstickExperimentTrials.addManipulationsTrialF(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_G)) {
            MatchstickExperimentTrials.addManipulationsTrialG(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_H)) {
            MatchstickExperimentTrials.addManipulationsTrialH(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_I)) {
            MatchstickExperimentTrials.addManipulationsTrialI(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_J1)) {
            MatchstickExperimentTrials.addManipulationsTrialJ1(plan);
        } else if (trialName.equals(MatchstickExperimentTrials.TRIAL_J2)) {
            MatchstickExperimentTrials.addManipulationsTrialI(plan);
        } else {
            throw new RuntimeException("Unrecognised trial name: '" + trialName + "'");
        }

        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(plan.getParameterTemplate());
        }
        return new MatchstickExperiment(simulation, plan);

    }

    public static ExperimentPlan createDefaultPlan(String name) {
        return createDefaultPlan(name, new CartesianDimensions(200));

    }
    public static ExperimentPlan createDefaultPlan(String name, CartesianDimensions landscapeSize) {
        ExperimentPlan plan = new ExperimentPlan(name);

//        SimulationCategory.addSimulationParameters(plan);
        Category environment = EnvironmentParameters.addEnvironmentParameters(plan);
        LandscapeParameters.addLandscapeParameters(environment, landscapeSize.getDoubleWidth());


        return plan;
    }

    public static final String EXP_1D_NAME = "exp01d";
}
