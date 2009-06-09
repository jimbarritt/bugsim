/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.function.IFunction;

public interface ISignalFunction extends IFunction {

    double calculateSensoryInformationValue(ISignalSource signalSource, double distanceToAttractor);
}
