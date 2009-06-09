/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.experiment1b;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.IExperimentFactory;
import com.ixcode.framework.simulation.model.Simulation;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1bFactory implements IExperimentFactory {




    public IExperiment createExperiment(String trialName) {
        Simulation simulation = new Simulation();
        ExperimentPlan plan;
        if (trialName.equals(Experiment1bParameters.TRIAL_DEBUG)) {
            plan = Experiment1bPlanFactory.createExperiment1bPlanDebug();
        } else if (trialName.equals(Experiment1bParameters.TRIAL_A)){
            plan = Experiment1bPlanFactory.createExperiment1bPlanTrialA();
        } else if (trialName.equals(Experiment1bParameters.TRIAL_B)){
            plan = Experiment1bPlanFactory.createExperiment1bPlanTrialB();
        } else {
            throw new RuntimeException("Unrecognised trial name: " + trialName);
        }



        return new Experiment1b(simulation, plan);

    }

}
