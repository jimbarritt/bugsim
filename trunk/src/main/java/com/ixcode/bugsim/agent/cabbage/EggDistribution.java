/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.cabbage;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;
import com.ixcode.framework.simulation.model.Simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 2, 2007 @ 8:30:50 PM by jim
 */
public class EggDistribution {

    public EggDistribution() {
    }

    public long getTotalEggs() {
        return _eggs.size();
    }

    public RectangularCoordinate getEggLocation(int eggIndex) {
        return getEgg(eggIndex).getLocation();
    }

    public ForagerEgg getEgg(int iEgg) {
        return (ForagerEgg)_eggs.get(iEgg);
    }

    public List getEggs() {
        return _eggs;
    }

    public void removeEgg(ForagerEgg egg) {
        _eggs.remove(egg);
        _deadEggs.add(egg);
    }

    /**
     * @todo at some point going to have to deal with eggs from multiple types of forager...
     * @param cabbageAgent
     */
    public void addEggs(CabbageAgent cabbageAgent) {
        for (int iEgg=0;iEgg<cabbageAgent.getEggCount();iEgg++) {
            _eggs.add(new ForagerEgg(cabbageAgent.getResourceId(), cabbageAgent.getLocation().getCoordinate()));
        }
    }

    public String toString() {
        return "[EggDistribution]: totalEggs=" + getTotalEggs();
    }

    public int getTotalDeadEggs() {
        return _deadEggs.size();
    }

    /**
     * Need to optimise this its going to be way slow! need an index of resource ids
     * @param egg
     * @param simulation
     * @return
     */
    public IResourceAgent findResource(ForagerEgg egg, Simulation simulation) {
        IResourceAgent found = null;
        List agents = simulation.getLiveAgents(CabbageAgentFilter.INSTANCE);
        for (Iterator itr = agents.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
            if (egg.getCabbageId() == cabbageAgent.getResourceId()) {
                found = cabbageAgent;
                break;
            }
        }
        return found;
    }

    private List _eggs = new ArrayList();
    private List _deadEggs= new ArrayList();
}
