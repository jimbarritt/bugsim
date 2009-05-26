/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : Generates Birth locations in a deterministic manner around a border.
 * these can then be passed into the ReleasePredefinedStrategy.
 */
public class DeterministicReleaseParameterGenerator {

    /**
     * If degrees turns out to not be accurate enough we might need to move down to radians
     *
     * @param segmentSizeDegrees          how far around the boundary do you want to generate  ? between 0 and <360
     * @param segmentIncrementDegrees divisions inbetween generated start locations.
     * @param bounds                  the boundary bounds
     * @param isCircular              is the boundary circular
     * @param moveLength
     * @return list of ReleaseInitialisationParameters objects
     */
    public static List generateBirthParameters(int startI, int segmentSizeDegrees, int segmentIncrementDegrees, int headingIncrementDegrees, boolean isCircular, double moveLength) {


        if (!isCircular) {
            throw new IllegalStateException("Cannot deal with non circular boundaries yet!");
        }

        if (log.isInfoEnabled()) {
            log.info("Generating birth parameters...");
        }

        // the actual x and y locations dont matter because the PredefinedBirth strategy works it all out again based on I
        double dimension = 10d;
        CartesianBounds bounds = new CartesianBounds(0, 0, dimension, dimension);
        List parameters = new ArrayList();
        double r = bounds.getRadiusOfInnerCircle();
        RectangularCoordinate circleCentre = bounds.getCentre();

        for (int i = startI; i <= segmentSizeDegrees; i += segmentIncrementDegrees) {
            for (int h = i + 90; h <= i + 270; h += headingIncrementDegrees) {
                RectangularCoordinate location = circleCentre.moveTo(new AzimuthCoordinate(i, r));
                ReleaseInitialisationParameters p = new ReleaseInitialisationParameters(location.getDoubleX(), location.getDoubleY(), h, moveLength, i);

                if (log.isDebugEnabled()) {
                    log.debug("Generated BirthParameter : " + p);
                }

                parameters.add(p);
            }
                                               
        }

        if (log.isInfoEnabled()) {
            log.info("Generated " + parameters.size() + " parameters.");
        }

        return parameters;

    }

    private static final Logger log = Logger.getLogger(DeterministicReleaseParameterGenerator.class);
}
