/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment;

import com.ixcode.bugsim.agent.butterfly.population.IPopulationFactory;
import com.ixcode.bugsim.agent.butterfly.population.IPopulationWeb;
import com.ixcode.bugsim.agent.cabbage.layout.IResourceLayout;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IBugsimExperiment extends ISimulationExperiment  {

    IPopulationWeb getPopulationWeb();

    IResourceLayout getResourceLayout();    


}
