/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model;

/**
 * Description : Implement this if you support being passed a plan in rather than creating on yourself...
 * Created     : Feb 9, 2007 @ 6:04:56 PM by jim
 */
public interface ILoadedExperimentFactory extends IExperimentFactory {

    IExperiment createExperiment(ExperimentPlan plan);

}
