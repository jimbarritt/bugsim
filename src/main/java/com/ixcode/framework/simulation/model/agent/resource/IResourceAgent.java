/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.resource;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.List;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Apr 25, 2007 @ 10:20:14 AM by jim
 */
public interface IResourceAgent extends IPhysicalAgent {
    long getEggCount();

    void setEggCount(long eggCount);

    void addEgg();

    void resetEggCount();

    long getResourceId();

}
