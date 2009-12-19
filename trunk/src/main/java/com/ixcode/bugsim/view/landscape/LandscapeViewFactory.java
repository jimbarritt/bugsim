/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.IExperiment;

import javax.swing.*;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface LandscapeViewFactory {

    JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController);

    boolean isShowLandscapeView();
}
