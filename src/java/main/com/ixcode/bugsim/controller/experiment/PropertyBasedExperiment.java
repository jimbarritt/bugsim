/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.framework.simulation.model.Simulation;

/**
 *  Description : Configured Completely via properties this experiment should be the only one we need eventually
 * @deprecated
 */
public class PropertyBasedExperiment extends ExperimentBase {


    public PropertyBasedExperiment(Simulation simulation) {
        super(null, simulation, null, Long.MAX_VALUE, 10, null, null);

    }

    protected String initialiseExperiment(Simulation simulation, ExperimentProperties parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
