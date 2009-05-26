/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.framework.math.geometry.CartesianBounds;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * TestCase for class : DeterministicReleaseParameterGenerator
 */
public class DeterministicReleaseParameterGeneratorTestCase extends TestCase {


    public void testGenerateBirthParameters() {
        double dimension = 10d;
        CartesianBounds bounds = new CartesianBounds(0, 0, dimension, dimension);
        double moveLength = bounds.getRadiusOfInnerCircle();
        List params = DeterministicReleaseParameterGenerator.generateBirthParameters(0, 90, 1, 1 ,true, moveLength);
        assertNotNull(params);

    }


    private static final Logger log = Logger.getLogger(DeterministicReleaseParameterGeneratorTestCase.class);

}
