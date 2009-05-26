/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;

/**
 *  Description : Pauses the experiment.
 */
public class PauseExperimentAction extends ExperimentControllerActionBase {

    public PauseExperimentAction(ExperimentController controller) {
        super("Pause", controller);
    }

    protected void executeControllerAction(ExperimentController controller) {
        controller.pause();
    }

}
