/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.IExperiment;

public interface IExperimentListener {

    void experimentInitialised(IExperiment source, ExperimentPlan plan);


    void iterationInitialised(IExperiment source, ParameterMap currentParameters, ExperimentProgress experimentProgress);
}
