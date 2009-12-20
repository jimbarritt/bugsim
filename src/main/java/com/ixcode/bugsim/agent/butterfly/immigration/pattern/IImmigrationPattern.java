/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.immigration.pattern;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Feb 26, 2007 @ 9:07:41 PM by jim
 */
public interface IImmigrationPattern extends IParameterisedStrategy {

    RectangularCoordinate nextReleaseLocation();

    double nextReleaseAzimuth(RectangularCoordinate releaseLocation, double moveLength);


    /**
     * This is shit here - has polluted the object model - need it to go on the Movement Strategy, but then maybe the initial heading is the same
     * @return
     * @param movementStrategy
     */
    double nextReleaseMoveLength(IMovementStrategy movementStrategy);

    /**
     * This is the angle around the release border - not strictly good up in this interface or indeed on the strategy
     * @todo work out a way to do this somewhere else!!
     * @return
     */
    double nextReleaseI();

    long getNumberToRelease();

    long getEggLimit();


}
