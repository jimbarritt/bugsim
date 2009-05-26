/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information.function;

import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GaussianDistributionGradientFunction extends GaussianDistributionFunction {

    public static final String NAME = "Normal Distribution Gradient";


    public GaussianDistributionGradientFunction() {
        super(NAME, 180, 1);
    }

    public double calculateSensoryInformationValue(ISignalSource signalSource, double distanceToAttractor) {
        double x = distanceToAttractor;
        double mu = getMean();
        double sd = getStandardDeviation();

        double exponent = Math.pow(x-mu, 2) / (2 * sd);

        double sdCubed = Math.pow(sd, 3);

        return  -((Math.exp(-exponent) * (SQRT_TWO_PI * (x-mu))) / sdCubed);


    }

    private static final double SQRT_TWO_PI = Math.sqrt(TWO_PI);
}
