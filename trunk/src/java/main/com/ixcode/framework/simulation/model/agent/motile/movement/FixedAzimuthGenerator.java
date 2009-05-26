/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.DirectionOfChange;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 13, 2007 @ 1:18:09 PM by jim
 */
public class FixedAzimuthGenerator extends AzimuthGeneratorBase {
    public FixedAzimuthGenerator(double fixedAzimuth) {
        _fixedAzimuth = fixedAzimuth;
    }

    public CourseChange generateCourseChange(double currentDirection) {
        double change = AzimuthCoordinate.calculateAngularDistanceFrom(currentDirection, _fixedAzimuth);
        DirectionOfChange direction = (change <0) ? DirectionOfChange.ANTI_CLOCKWISE : DirectionOfChange.CLOCKWISE;
        return new CourseChange(_fixedAzimuth, direction, change);
    }

    private double _fixedAzimuth;
}
