/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.simulation.model.landscape.Location;

public interface ISignalSource {

    double getSize();

    boolean withinRange(Location location);

    Location getLocation();
}
