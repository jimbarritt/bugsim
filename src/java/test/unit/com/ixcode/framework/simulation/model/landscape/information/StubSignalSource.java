/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.simulation.model.landscape.Location;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class StubSignalSource implements ISignalSource {

    public StubSignalSource(double x, double y) {
        _location = new Location(x, y);
    }

    public double getSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean withinRange(Location location) {
        return true;
    }

    public Location getLocation() {
        return _location;
    }

    private Location _location;
}
