/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.experiment.model.ExperimentProgress;

public interface IExperimentProgressListener {

    void progressNotification(ExperimentProgress progress);

}
