/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.random.MonteCarloRandom;
import com.ixcode.framework.math.random.WrappedCauchyRandom;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignal;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.WrappedCauchyAzimuthStrategy;

import java.util.Random;
import java.util.Map;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 *  Description : Generates Random Headings using a von mises distribution.
 */
public class WrappedCauchyAzimuthGenerator extends AzimuthGeneratorBase {

    public WrappedCauchyAzimuthGenerator() {
    }

    public WrappedCauchyAzimuthGenerator(Random random, double k, double n) {
        super(random);
        _k = k;
        _n = n;
        _K = WrappedCauchyAzimuthGenerator.FORMAT2D.format(_k);
        _N = WrappedCauchyAzimuthGenerator.FORMAT2D.format(_n);

        double mu = 0; // Mean is always zero
        DiscreetValueMap pdist = WrappedCauchyRandom.createProbabilityDensity(_n, _k, mu);
       _monteCarloRandom = new MonteCarloRandom(pdist, super.getRandom());
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
         super.initialise(strategyP, params, initialisationObjects);

        WrappedCauchyAzimuthStrategy vms = (WrappedCauchyAzimuthStrategy)AzimuthStrategyFactory.createAzimuthStrategy(strategyP, params, false);

        _k= vms.getAngleOfTurnRho();
        _maxK = vms.getMaxRho();
        _K = FORMAT2D.format(_k);

        _n = vms.getResolutionN();
        _N = FORMAT2D.format(_n);

        double mu = 0; // Mean is always zero
        DiscreetValueMap pdist = WrappedCauchyRandom.createProbabilityDensity(_n, _k, mu);
       _monteCarloRandom = new MonteCarloRandom(pdist, super.getRandom());
    }

    public String getParameterSummary() {
        return "K=" + _K + ", N=" + _N;
    }

    public CourseChange generateCourseChange(double currentDirection) {
        return Geometry.createCourseChange(currentDirection, Math.toDegrees(_monteCarloRandom.nextDouble()));
    }

    public IAzimuthGenerator modifyAzimuthGenerator(VisualSignal visualSignal, double calculatedAzimuth) {
        int indexOfValue = visualSignal.getSignalInputs().findIndexOfKey(calculatedAzimuth, true);// can tell us what the signal strength of the direction we chose is if we like to use it...
        double signalValue = visualSignal.getSignalInputs().getValue(indexOfValue);

//        if (log.isInfoEnabled()) {
//            log.info("Current Signal Value: " + signalValue);
//        }
        IAzimuthGenerator generator = null;
        boolean lessThanThreshold = DoubleMath.precisionLessThan(signalValue, super.getVisualSignalNoiseThreshold(), visualSignal.getSignalInputs().getPrecision());
        if (lessThanThreshold) {
            double psi = (_maxK - _k) / _maxK;

            double newk = _maxK - super.calculateSignalNoise(_k, psi, _maxK);
            generator = new WrappedCauchyAzimuthGenerator(super.getRandom(), newk, _n);
//            if (log.isInfoEnabled()) {
//                log.info("Adding noise k=" + newk);
//            }
        } else {
            generator = new FixedAzimuthGenerator(calculatedAzimuth);
        }

        return generator;
    }

    private static final Logger log = Logger.getLogger(WrappedCauchyAzimuthGenerator.class);


    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _K;
    private double _k;
    private double _n;
    private String _N;
    private MonteCarloRandom _monteCarloRandom;
    private double _maxK;
}
