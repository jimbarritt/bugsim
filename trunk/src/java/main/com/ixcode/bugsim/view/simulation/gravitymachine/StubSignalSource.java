/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
final class StubSignalSource implements ISignalSource {

    public StubSignalSource(double mass) {
        _mass = mass;
    }

    public double getSize() {
        return _mass;
    }

    public boolean withinRange(Location location) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Location getLocation() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private double _mass;
}
