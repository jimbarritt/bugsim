/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment1d;

import com.ixcode.bugsim.view.experiment.IExperimentProgressFactory;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1dProgressFactory implements IExperimentProgressFactory {

    public JComponent createExperimentProgressPanel(IExperiment experiment) throws JavaBeanException {
        return  new Experiment1dProgressPanel((ISimulationExperiment)experiment);
    }
}
