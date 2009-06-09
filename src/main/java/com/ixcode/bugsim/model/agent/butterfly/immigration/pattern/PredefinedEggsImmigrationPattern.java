/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 26, 2007 @ 9:09:05 PM by jim
 */
public class PredefinedEggsImmigrationPattern implements IImmigrationPattern {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {

    }

    public long getEggLimit() {
        return -1;
    }

    public long getNumberToRelease() {
        return 0;
    }

    public String getParameterSummary() {
        return null;
    }

    public RectangularCoordinate nextReleaseLocation() {
        return null;
    }

    public double nextReleaseAzimuth(RectangularCoordinate releaseLocation, double moveLength) {
        return 0;
    }

    public double nextReleaseMoveLength(IMovementStrategy movementStrategy) {
        return movementStrategy.getInitialMoveLength();
    }

    public double nextReleaseI() {
        return 0;
    }
}
