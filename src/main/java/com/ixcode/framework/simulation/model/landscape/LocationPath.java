/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape;

import com.ixcode.framework.math.geometry.ICoordinatePath;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * Implements an interface so we can give out a read only version of the path
 */
public class LocationPath implements ICoordinatePath {

    public LocationPath(Location origin) {
        addLocation(origin);
    }



    public List getCoordinates() {
        return _coordinates;
    }

    public void addLocation(Location newLocation) {
      _locations.add(newLocation);
      _coordinates.add(newLocation.getCoordinate());
    }

    public Location getLocation(int iStep) {
        Location location = null;
        if (iStep>_locations.size()) {
            location = (Location)_locations.get(_locations.size()-1);
        } else {
            location = (Location)_locations.get(iStep);
        }
        return location;
    }

    private List _coordinates = new ArrayList();
    private List _locations = new ArrayList();
}
