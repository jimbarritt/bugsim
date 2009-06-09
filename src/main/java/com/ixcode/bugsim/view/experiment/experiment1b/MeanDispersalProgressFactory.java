/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment1b;

import com.ixcode.bugsim.model.experiment.experiment1b.Experiment1b;
import com.ixcode.bugsim.view.experiment.IExperimentProgressFactory;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.javabean.JavaBeanException;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MeanDispersalProgressFactory implements IExperimentProgressFactory {

    public JComponent createExperimentProgressPanel(IExperiment experiment) throws JavaBeanException {
        return  new MeanDispersalExperimentProgressPanel((Experiment1b)experiment);
    }
}
