/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experimentX;

import com.ixcode.bugsim.experiment.IBugsimExperiment;
import com.ixcode.bugsim.view.experiment.IExperimentProgressFactory;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.javabean.JavaBeanException;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentXProgressFactory implements IExperimentProgressFactory {

    public JComponent createExperimentProgressPanel(IExperiment experiment) throws JavaBeanException {
        return  new ExperimentXProgressPanel((IBugsimExperiment)experiment);
    }
}
