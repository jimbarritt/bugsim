/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.util.List;

/**
 * Description : Given a list of ISignalSource's will tell you the current absolute value of the information
 * and the current gradient for a particular location.
 * The locations are absolute, not relative
 */
public interface ISignalSurfaceCalculator {

    double calculateSurfaceHeight(List informationSources, RectangularCoordinate location);

    double calculateSurfaceGradient(List informationSources, RectangularCoordinate location);

    ISignalFunction getSignalFunction();
}
