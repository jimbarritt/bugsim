/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information.function;

import com.ixcode.bugsim.experiment.parameter.resource.CabbageParameters;
import com.ixcode.framework.math.function.FunctionBase;
import com.ixcode.framework.math.random.GaussianRandom;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;

import java.util.Map;

/**
 * Description : Uses the normal distribution to create a curve
 */
public class GaussianDistributionFunction extends FunctionBase implements ISignalFunction, IParameterisedStrategy {

    public static final String NAME = "Normal Distribution";

    public static final String PROP_MEAN = "mean";
    public static final String PROP_STD_DEV = "stdDev";
    public static final String PROP_MAGNIFICATION = "magnification";

    /**
     * Defaults are
     * double standardDeviation = 1e7;
     * double magnification = 1e5;
     *
     * @param strategyParameter
     * @param params
     * @param initialisationObjects
     */
    public void initialise(StrategyDefinitionParameter strategyParameter, ParameterMap params, Map initialisationObjects) {
        double standardDeviation = CabbageParameters.getSignalSD(strategyParameter);
        double magnification = CabbageParameters.getSignalMagnification(strategyParameter);

        setStandardDeviation(standardDeviation);
        setMagnification(magnification);

    }

    public String getParameterSummary() {
        return "";
    }

    public GaussianDistributionFunction() {
        this(20, 1);
    }

    public GaussianDistributionFunction(double standardDeviation, double magnification) {
        this(NAME, standardDeviation, magnification);

    }

    protected GaussianDistributionFunction(String name, double standardDeviation, double magnification) {
        super(name);
        super.addProperty(PROP_MEAN, Double.class);
        super.addProperty(PROP_STD_DEV, Double.class);
        super.addProperty(PROP_MAGNIFICATION, Double.class);
        setMean(0);
        setStandardDeviation(standardDeviation);
        setMagnification(magnification);
    }

    /**
     * Not sure how mass should affect it - if we simply multiply scale by mass its too big an effect
     *
     * @param signalSource
     * @param distanceToAttractor
     * @return result
     */
    public double calculateSensoryInformationValue(ISignalSource signalSource, double distanceToAttractor) {
        double x = distanceToAttractor;
        double mu = getMean();
        double sd = getStandardDeviation();
        double magnification = getMagnification();
        return GaussianRandom.calculateGaussianP(x, mu, sd) * magnification;
    }

    public void setMean(double mean) {
        super.setPropertyDouble(PROP_MEAN, mean);
    }

    public void setStandardDeviation(double sd) {
        super.setPropertyDouble(PROP_STD_DEV, sd);
    }

    public void setMagnification(double magnification) {
        super.setPropertyDouble(PROP_MAGNIFICATION, magnification);
    }

    public double getMagnification() {
        return super.getPropertyDoubleValue(PROP_MAGNIFICATION);
    }

    public double getMean() {
        return super.getPropertyDoubleValue(PROP_MEAN);
    }

    public double getStandardDeviation() {
        return super.getPropertyDoubleValue(PROP_STD_DEV);
    }


    protected static final double TWO_PI = 2 * Math.PI;


}
