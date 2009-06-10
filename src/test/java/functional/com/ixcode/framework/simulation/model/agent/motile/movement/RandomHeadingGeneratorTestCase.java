/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.bugsim.fixture.*;
import com.ixcode.framework.math.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.stats.*;
import junit.framework.*;

import java.util.*;

@FunctionalTest
public class RandomHeadingGeneratorTestCase extends TestCase {

    public void testGenerateCorrelatedRandomDirection() {

        CourseChange newCourse = _generator.generateCourseChange(180);
        assertTrue("less than 360", newCourse.getNewAzimuth() <=360);
        assertTrue("greater than 0", newCourse.getNewAzimuth() >=0);
    }

    public void testMassiveNumbers() {

        IAzimuthGenerator uniformGenerator = new UniformAzimuthGenerator(new Random());
        int replicants = 1000000;
        double results[] = new double[replicants];
        for (int i=0;i<replicants;++i) {
            results[i] = uniformGenerator.generateCourseChange(0).getNewAzimuth();
        }

        double max = DoubleMath.maxOf(results);
        double min = DoubleMath.minOf(results);

        System.out.println("Max: " + max + ", min: " + min);
        assertTrue("Max<359.999 actual is " + max, (max<359.9999));
        assertTrue("Min>0", (min>0));
    }



    public void testGenerateCorrelatedRandomDirectionIsNormal() {
        int replicants = 1000000;
        double[] results = new double[replicants];
        double startDirection = 180;
        final double STD_DEVIATION = 20;
        double currentHeading =startDirection;
        double previousHeading = startDirection;
        for (int i=0;i<replicants;++i) {
            CourseChange courseChange = _generator.generateCourseChange(currentHeading);;
            currentHeading = courseChange.getNewAzimuth();
            results[i] = courseChange.getAngleOfChange();

            previousHeading = currentHeading;
        }
        double actualSD = SummaryStatistics.calculateStdDeviationDouble(results);
        assertEquals("Std Deviation", STD_DEVIATION, actualSD, 0.05);
    }

    private IAzimuthGenerator _generator = new GaussianAzimuthGenerator(new Random(), 20);


}
