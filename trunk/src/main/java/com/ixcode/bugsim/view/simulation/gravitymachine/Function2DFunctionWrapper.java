/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import org.jfree.data.function.Function2D;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
class Function2DFunctionWrapper implements Function2D {
    private ISignalFunction _function;
    private double _massOfAttractor;

    public Function2DFunctionWrapper(ISignalFunction function, double massOfAttractor) {
        _function = function;
        _massOfAttractor = massOfAttractor;
        _attractor = new StubSignalSource(_massOfAttractor);

    }

    public double getValue(double x) {
        if (_function != null) {
            double y = _function.calculateSensoryInformationValue(_attractor, x);
//            System.out.println("x=" + x + ",y=" + y);
            return y;
        }
        return 0;

    }



    private StubSignalSource _attractor;
}
