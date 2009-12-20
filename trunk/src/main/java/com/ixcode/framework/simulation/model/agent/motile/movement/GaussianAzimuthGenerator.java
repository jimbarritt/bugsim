/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.random.GaussianRandom;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;

import java.util.Random;
import java.util.Map;
import java.text.DecimalFormat;

/**
 * Description :
 */
public class GaussianAzimuthGenerator extends AzimuthGeneratorBase {

    public GaussianAzimuthGenerator() {
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
         super.initialise(strategyP, params, initialisationObjects);
        _standardDeviation = ButterflyParameters.getAngleOfTurn(strategyP);
        _A = FORMAT2D.format(_standardDeviation);

    }

    public String getParameterSummary() {
        return "A=" + _A;
    }

    public GaussianAzimuthGenerator(Random random, double standardDeviation) {
        super(random);
        _standardDeviation = standardDeviation;
    }

    /**
     * Generates a distribution of angles with a standard deviation you give it
     *
     * @param currentDirection
     * @return
     */
    public CourseChange generateCourseChange(double currentDirection) {
        double thetaChange = GaussianRandom.generateGaussian(super.getRandom(), _standardDeviation, 0);
        return Geometry.createWrappedCourseChange(currentDirection, thetaChange);

    }


    public double getStdDeviation() {
        return _standardDeviation;
    }

    public void setStdDeviation(double sd) {
        _standardDeviation = sd;
    }


    private double _standardDeviation;
    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _A;

}
