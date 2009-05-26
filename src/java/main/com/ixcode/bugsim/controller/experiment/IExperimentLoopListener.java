/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 * @deprecated
 */
public interface IExperimentLoopListener {

    

    void nextIteration(long loopIndex, IExperimentLoop loop, IExperiment experiment);
}
