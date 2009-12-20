/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter.resource.layout.predefined;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 *  Description : Used to initialise cabbages!
 */
public class CabbageInitialisationParameters {

    public CabbageInitialisationParameters(long cabbageId, RectangularCoordinate location, double radius) {
        _cabbageId = cabbageId;
        this._location = location;

        _radius = radius;
    }


    public long getCabbageId() {
        return _cabbageId;
    }

    public double getX() {
        return _location.getDoubleX();
    }

    public double getY() {
        return _location.getDoubleY();
    }



    public String toString() {
        return _format.format(this);
    }

    public double getRadius() {
        return _radius;
    }

    long _cabbageId;
     RectangularCoordinate _location;

    private CabbageInitialisationParametersFormat _format = new CabbageInitialisationParametersFormat();

    private double _radius;
}
