/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.ExperimentProgress;

import java.io.IOException;

public interface IExperimentReporter {

    void reportResults(String rootPath,String experimentId, IExperiment experiment,
                       ParameterMap currentParameters, ExperimentProgress progress,
                       boolean paramStructureChanged, boolean outputDetails) throws IOException;

}
