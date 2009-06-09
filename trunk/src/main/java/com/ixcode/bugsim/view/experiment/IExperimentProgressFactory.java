/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.javabean.JavaBeanException;

import javax.swing.*;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IExperimentProgressFactory {


    JComponent createExperimentProgressPanel(IExperiment experiment) throws JavaBeanException;
}
