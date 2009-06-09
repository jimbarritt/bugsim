/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;

import java.io.File;
import java.io.IOException;

public interface IReportWriter {


    void write(File output, IExperiment experiment, String experimentOutputId, ParameterMap currentParameters, ExperimentProgress progress) throws IOException;
}
