/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment2a;

import com.ixcode.bugsim.model.experiment.IBugsimExperiment;
import com.ixcode.bugsim.view.experiment.IExperimentProgressFactory;
import com.ixcode.bugsim.view.experiment.experiment1a.EdgeEffectExperimentProgressPanel;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.javabean.JavaBeanException;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Experiment2aProgressFactory implements IExperimentProgressFactory {

    public JComponent createExperimentProgressPanel(IExperiment experiment) throws JavaBeanException {
        return  new EdgeEffectExperimentProgressPanel((IBugsimExperiment)experiment);
    }
}
