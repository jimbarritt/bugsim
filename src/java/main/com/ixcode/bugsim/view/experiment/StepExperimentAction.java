/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;

/**
 *  Description : Pauses the experiment.
 */
public class StepExperimentAction extends ExperimentControllerActionBase {

    public StepExperimentAction(ExperimentController controller) {
        super("Step", controller);
    }

    protected void executeControllerAction(ExperimentController controller) {
        controller.step();
    }

}
