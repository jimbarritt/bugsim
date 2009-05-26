/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

/**
 * @deprecated
 */
public interface IExperimentLoop {
    boolean nextIteration(long currentLoopIndex, IExperiment experiment);

    void initialiseExperimentProperties(long currentLoopIndex, IExperiment experiment);

    String getName();
    String getId();

    /**
     *
     * @todo could add a parameter so that we only chnage the property every certain number of loops
     * @param propertyName
     * @param startValue
     * @param maxValue
     * @param increment
     */
    void addPropertyChanger(String propertyName, Object startValue, Object maxValue, Object increment);

    void addSubLoop(IExperimentLoop subLoop);

    void addLoopListener(IExperimentLoopListener loopListener);

    boolean isLoopComplete(long currentLoopIndex, IExperiment experiment);
}
