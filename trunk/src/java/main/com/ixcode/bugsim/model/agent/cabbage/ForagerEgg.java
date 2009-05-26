/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.cabbage;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 2, 2007 @ 9:04:27 PM by jim
 *
 * We cannot store the original cabbag ebecause we will remove all eggs from this so better just to remember the id. also cabbages might get
 * removed or something.
 * @todo at some point we will need to be able to get all the other eggs on the same plant ads this.
 */
public class ForagerEgg {

    public ForagerEgg(long cabbageId, RectangularCoordinate location) {
        _cabbageId = cabbageId;
        _location = location;
    }

    public RectangularCoordinate getLocation() {
        return _location;
    }

    public long getCabbageId() {
        return _cabbageId;
    }


    private RectangularCoordinate _location;

    private long _cabbageId;
}
