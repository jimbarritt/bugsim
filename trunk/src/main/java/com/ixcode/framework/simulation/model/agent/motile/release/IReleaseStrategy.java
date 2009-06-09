/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * @deprecated
 */
public interface IReleaseStrategy extends IParameterisedStrategy {





    RectangularCoordinate generateInitialLocation();

    double generateInitialAzimuth();


    /**
     * This is shit here - has polluted the object model - need it to go on the Movement Strategy, but then maybe the initial heading is the same
     * @return
     */
    double generateBirthMoveLength();

    /**
     * This is the angle around the release border - not strictly good up in this interface or indeed on the strategy
     * @todo work out a way to do this somewhere else!!
     * @return
     */
    double generateReleaseI();
}
