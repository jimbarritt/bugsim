/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 * @deprecated
 */
public interface IExperimentReporter extends IExperimentListener, IExperimentLoopListener {

    boolean isOutputResults();

    void setOutputResults(boolean outputResults);

    /**
     * It is up to the individual experiments as to how exactly they output
     *
     * @param path
     */
    void setOutputRootPath(String path);


}
