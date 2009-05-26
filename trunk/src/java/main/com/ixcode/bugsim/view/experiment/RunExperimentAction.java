/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;

/**
 *  Description : Pauses the experiment.
 */
public class RunExperimentAction extends ExperimentControllerActionBase {

    public RunExperimentAction(ExperimentController controller) {
        super("Run", controller);
    }

    protected void executeControllerAction(ExperimentController controller) {
        controller.run();
    }

}
