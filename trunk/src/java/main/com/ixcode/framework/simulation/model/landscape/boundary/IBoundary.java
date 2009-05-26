/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IBoundary {

    boolean isInside(RectangularCoordinate coordinate);

    boolean isOnEdge(RectangularCoordinate coordinate);

    boolean isOutside(RectangularCoordinate coordinate);


    BoundaryShape getShape();


    CartesianBounds getBounds();

    RectangularCoordinate getLocation();

    CartesianDimensions getDimension();

    
}
