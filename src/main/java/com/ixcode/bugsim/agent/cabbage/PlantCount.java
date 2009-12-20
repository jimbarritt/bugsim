/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.cabbage;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 23, 2007 @ 1:01:35 PM by jim
 */
public class PlantCount {

    public PlantCount(long plantId, long eggCount) {
        _plantId = plantId;
        _eggCount = eggCount;
    }

    public long getPlantId() {
        return _plantId;
    }

    public long getEggCount() {
        return _eggCount;
    }

    private long _plantId;
    private long _eggCount;
}
