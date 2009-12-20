/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage.signal;

import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurface;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * @todo move all this stuff to do with signal surfaces into the framework.
 */
public interface ISignalSurfaceFactory {

    ISignalSurface createSignalSurface(Landscape landscape);
}
