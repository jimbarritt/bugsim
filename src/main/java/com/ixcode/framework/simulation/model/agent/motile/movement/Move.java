/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.math.geometry.CourseChange;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Move {

    public Move(Location location, CourseChange courseChange, double distance) {
        _location = location;
        _azimuth = courseChange.getNewAzimuth();
        _distance = distance;
        _coursechange = courseChange;
    }

    public Location getLocation() {
        return _location;
    }

    public double getAzimuth() {
        return _azimuth;
    }

    public double getDistance() {
        return _distance;
    }

    public CourseChange getCourseChange() {
        return _coursechange;
    }

    private Location _location;
    private double _azimuth;
    private double _distance;
    private CourseChange _coursechange;
}
