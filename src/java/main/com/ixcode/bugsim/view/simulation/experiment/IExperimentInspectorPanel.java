/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.experiment;

import com.ixcode.bugsim.controller.experiment.IExperiment;

import javax.swing.*;

public interface IExperimentInspectorPanel {

    JComponent getViewComponent();

    void setExperiment(IExperiment experiment);
}
