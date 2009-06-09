/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;

/**
 * @deprecated use model.experiment.IExperiment now.
 */
public interface IExperiment extends ISimulationListener {

    
    LandscapeFrame createLandscapeFrame();

    void resetExperiment();

    void stopExperiment();

    void run();

    void runFor(long timesteps);

    boolean isComplete();

    void setMaximumTimestep(long maxtimestep);

    void setTimestepDelay(long timestepDelay);


    void addExperimentListener(IExperimentListener listener);


    String getName();

    Simulation getSimulation();


    ExperimentProperties getProperties();


    boolean hasExperimentReporter();

    IExperimentReporter getExperimentReporter();



}
