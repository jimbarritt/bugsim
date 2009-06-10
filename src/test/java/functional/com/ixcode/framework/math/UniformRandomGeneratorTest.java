package com.ixcode.framework.math;

import com.ixcode.bugsim.fixture.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.random.*;
import org.apache.log4j.*;
import static org.junit.Assert.*;
import org.junit.*;

import java.util.*;

@FunctionalTest
public class UniformRandomGeneratorTest {

    private static final Logger log = Logger.getLogger(UniformRandomGeneratorTest.class);
    private static final Random RANDOM = new Random();


    
    @Test
    public void generatesUniformRandomDouble() {
        final int ITERATIONS = 100;

        double results[] = new double[ITERATIONS];
        for (int i = 0; i < ITERATIONS; ++i) {
            results[i] = UniformRandom.generateUniformRandomDouble(6, 1, RANDOM);
        }

        double max = DoubleMath.maxOf(results);
        double min = DoubleMath.minOf(results);
        System.out.println("N=" + ITERATIONS + ", max=" + max + ", min=" + min);
    }

    @Test
    public void generatesUniformRandomAngleDouble() {
        final int ITERATIONS = 1000;

        double results[] = new double[ITERATIONS];
        for (int i = 0; i < ITERATIONS; ++i) {
            results[i] = Geometry.generateUniformRandomAzimuthChange(RANDOM);

        }

        double max = DoubleMath.maxOf(results);

        double min = DoubleMath.minOf(results);

          if (log.isInfoEnabled()) {
            log.info("N=" + ITERATIONS + ", max=" + max + ", min=" + min);
        }
        assertTrue(max > min);
        assertTrue(DoubleMath.precisionLessThanEqual(max, Math.toDegrees(2 * Math.PI), DoubleMath.DOUBLE_PRECISION_DELTA));



    }
}
