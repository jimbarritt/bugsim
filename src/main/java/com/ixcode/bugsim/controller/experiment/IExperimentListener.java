/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 * @deprecated
 */
public interface IExperimentListener {

    void experimentStarted(IExperiment experiment);

    void timeStepExecuted(IExperiment experiment, long timestep);



    void experimentReset(IExperiment experiment);

    void experimentComplete(IExperiment experiment);

    void experimentStopped(IExperiment experiment);
}
