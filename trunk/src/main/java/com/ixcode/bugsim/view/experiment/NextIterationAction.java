/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;

/**
 *  Description : Pauses the experiment.
 */
public class NextIterationAction extends ExperimentControllerActionBase {

    public NextIterationAction(ExperimentController controller) {
        super("Next Itr", controller);
    }

    protected void executeControllerAction(ExperimentController controller) {
        controller.nextIteration();
        controller.fireProgressEvents();
    }

}
