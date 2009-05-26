/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 *  Description : So you dont have to implement all the methods!
 * @deprecated
 */
public abstract  class ExperimentListenerBase implements IExperimentListener {

    public void experimentStarted(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void timeStepExecuted(IExperiment experiment, long timestep) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentReset(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentComplete(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentStopped(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
