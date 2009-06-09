/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;

/**
 *  Description : Pauses the experiment.
 */
public class ResetExperimentAction extends ExperimentControllerActionBase {

    public ResetExperimentAction(ExperimentController controller) {
        super("Reset", controller);
    }

    protected void executeControllerAction(ExperimentController controller) {
        controller.reset();
    }

}
