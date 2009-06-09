/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.cabbage;

import java.util.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 23, 2007 @ 12:55:49 PM by jim
 */
public class EggCount {

    public EggCount(long timestep, Class agentClass) {
        _time = timestep;
        _agentClass = agentClass;
    }

    void countEggs(List cabbages) {
        _counts = new ArrayList();
        for (Iterator itr = cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbageAgent = (CabbageAgent)itr.next();
            PlantCount count = new PlantCount(cabbageAgent.getResourceId(),cabbageAgent.getEggCount());
            _counts.add(count);
            _totalEggs += count.getEggCount();
        }
    }

    public long getTotalEggs() {
        return _totalEggs;
    }

    public List getCounts() {
        return _counts;
    }

    public long getTime() {
        return _time;
    }

    public Class getAgentClass() {
        return _agentClass;
    }


    private long _totalEggs;
    private List _counts;
    private long _time;
    private Class _agentClass;
}

