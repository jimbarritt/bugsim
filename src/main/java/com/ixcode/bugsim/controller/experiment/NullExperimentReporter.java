/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated 
 */
public class NullExperimentReporter extends ExperimentListenerBase implements IExperimentReporter {

    public boolean isOutputResults() {
        return _outputResults;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOutputResults(boolean outputResults) {
        _outputResults = outputResults;
    }

    public void setOutputRootPath(String path) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void nextIteration(long loopIndex, IExperimentLoop loop, IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static final IExperimentReporter INSTANCE = new NullExperimentReporter();
    private boolean _outputResults;
}
