/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GravitationalCalculatorFactory {
    public static GravitationalCalculator createNullGravityMachine() {
        return new GravitationalCalculator(new ExponentialDecaySignalFunction(2, 1, .004), 0.000001);
    }
}
