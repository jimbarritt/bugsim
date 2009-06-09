/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information.function;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;
import junit.framework.TestCase;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExponentialDecayInformationFunctionTestCase extends TestCase {


    public void testCalculateForceOfAttraction_2() {

//        runTest(2, 100000, 1, 10, 10);
        runTest(2, 1000, 1, 100, 0.1);
        runTest(2, 1000, 1, 10, 10);
    }

    private void runTest(double gravitationalConstant, double scale, final double mass, double distance, double expectedF) {
        ISignalFunction func = new ExponentialDecaySignalFunction(gravitationalConstant, scale);

        ISignalSource signalSource = new ISignalSource() {
            public double getSize() {
                return mass;
            }

            public boolean withinRange(Location location) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Location getLocation() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        double f = func.calculateSensoryInformationValue(signalSource, distance);
        assertEquals(expectedF, f, 0.0000001);
    }


}
