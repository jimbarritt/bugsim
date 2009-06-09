/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface ISignalSurface {

    public SignalSample getInformationSample(RectangularCoordinate coordinate);


    String getName();

    void tidyUp();
}
