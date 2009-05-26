/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.cabbage.layout;

import com.ixcode.framework.simulation.model.Simulation;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IResourceLayout {

    public void createCabbages(Simulation simulation);

    public String getParameterSummary();

    public String getResourceLayoutGridName();


}
