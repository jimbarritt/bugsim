/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.experimentX.configuration;

import com.ixcode.bugsim.model.experiment.experimentX.IExperimentXConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.ExperimentX;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.framework.simulation.model.landscape.Landscape;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 1, 2007 @ 11:29:25 AM by jim
 */
public class LandscapeConfiguration implements IExperimentXConfiguration  {
    public void initialise(ExperimentX experimentX, BugsimParameterMap bugsimParams) {
        experimentX.getSimulation().getLandscape().initialise(bugsimParams.getLandscapeCategory());
    }
}
