/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1d;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.experiment.parameter.EnvironmentParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
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
public class Experiment1dFactory implements IExperimentFactory {



    public IExperiment createExperiment(String trialName) {


        Simulation simulation = new Simulation();
        ExperimentPlan plan = Experiment1dFactory.createDefaultPlan(Experiment1dFactory.EXP_1D_NAME, new CartesianDimensions(250));

        if (trialName.equals(Experiment1dTrials.TRIAL_DEBUG)) {
            Experiment1dTrials.addManipulationsTrialDebug(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_A)) {
            Experiment1dTrials.addManipulationsTrialA(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_B)) {
            Experiment1dTrials.addManipulationsTrialB(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_C)) {
            Experiment1dTrials.addManipulationsTrialC(plan);
        } else  if (trialName.equals(Experiment1dTrials.TRIAL_D)) {
            Experiment1dTrials.addManipulationsTrialD(plan);
        } else  if (trialName.equals(Experiment1dTrials.TRIAL_E)) {
            Experiment1dTrials.addManipulationsTrialE(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_F)) {
            Experiment1dTrials.addManipulationsTrialF(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_G)) {
            Experiment1dTrials.addManipulationsTrialG(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_H)) {
            Experiment1dTrials.addManipulationsTrialH(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_I)) {
            Experiment1dTrials.addManipulationsTrialI(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_J1)) {
            Experiment1dTrials.addManipulationsTrialJ1(plan);
        } else if (trialName.equals(Experiment1dTrials.TRIAL_J2)) {
            Experiment1dTrials.addManipulationsTrialI(plan);
        } else {
            throw new RuntimeException("Unrecognised trial name: '" + trialName + "'");
        }

        if (BugsimMain.isDebug()) {
            ParameterMapDebug.debugParams(plan.getParameterTemplate());
        }
        return new Experiment1d(simulation, plan);

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
