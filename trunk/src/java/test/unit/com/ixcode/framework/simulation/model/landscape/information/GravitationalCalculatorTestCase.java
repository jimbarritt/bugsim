/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinateTestCase;
import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GravitationalCalculatorTestCase extends TestCase {

    public void testCalculateNetDisplacement_single_1() {
        AzimuthCoordinate expected = new AzimuthCoordinate(0, 0.01);
        runSingleAttractorTest(0, 10, 0, 0, expected);
    }


   

    private void runSingleAttractorTest(double xAttractor, double yAttractor, double xAgent, double yAgent, AzimuthCoordinate expected) {
        List attractors = new ArrayList();
        attractors.add(new StubSignalSource(xAttractor, yAttractor));


        RectangularCoordinate affectedCoordinate = new RectangularCoordinate(xAgent, yAgent);
        RectangularCoordinate newCoordinate = _gravitationalCalculator.calculateNetDisplacement(attractors, affectedCoordinate);
        AzimuthCoordinate actual = affectedCoordinate.calculateAzimuthCoordinateTo(newCoordinate);
        System.out.println("Got " + actual);
        RectangularCoordinateTestCase.assertEquals(expected, actual);
    }

    private GravitationalCalculator _gravitationalCalculator = new GravitationalCalculator(new ExponentialDecaySignalFunction(2, 1), 0);
}
