/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment;

import com.ixcode.bugsim.experiment.experimentX.ExperimentXFactory;
import com.ixcode.bugsim.experiment.experimentX.ExperimentXTrials;
import com.ixcode.framework.simulation.experiment.ExperimentTemplateRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BugsimExperimentTemplateRegistry extends ExperimentTemplateRegistry {

    public BugsimExperimentTemplateRegistry() {
//        super.registerTemplate(ExperimentXTrials.TEMPLATE_DEFAULT, "Default Setup", "Basic random walker with simple resource pattern.", ExperimentXFactory.class, ExperimentXFactory.METHOD_DEFAULT_FACTORY, ExperimentXTrials.class,  ExperimentXTrials.METHOD_TRIAL_DEFAULT);
        super.registerTemplate(ExperimentXTrials.TEMPLATE_TRX, "Default Setup", "Basic Setup With common features",ExperimentXFactory.class, ExperimentXFactory.METHOD_DEFAULT_FACTORY, ExperimentXTrials.class,  ExperimentXTrials.METHOD_TRIAL_X);
        super.registerTemplate(ExperimentXTrials.TEMPLATE_TRD1_D, "Trial D1-D Setup", "Implements Multiple Manipulation of Release In Zone",ExperimentXFactory.class, ExperimentXFactory.METHOD_DEFAULT_FACTORY, ExperimentXTrials.class,  ExperimentXTrials.METHOD_TRIAL_TRD1_D);
        super.registerTemplate(ExperimentXTrials.TEMPLATE_TRX1, "Test Sensors", "Setup to test Sensory Perception",ExperimentXFactory.class, ExperimentXFactory.METHOD_DEFAULT_FACTORY, ExperimentXTrials.class,  ExperimentXTrials.METHOD_TRIAL_X1);

    }
}
