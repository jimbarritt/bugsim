/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.random.MonteCarloRandom;
import com.ixcode.framework.math.random.WrappedNormalRandom;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignal;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.WrappedNormalAzimuthStrategy;

import java.util.Random;
import java.util.Map;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 *  Description : Generates Random Headings using a von mises distribution.
 */
public class WrappedNormalAzimuthGenerator extends AzimuthGeneratorBase {

    public WrappedNormalAzimuthGenerator() {
    }

    public WrappedNormalAzimuthGenerator(Random random, double sd, double n, double tolerance) {
        super(random);
        _sd = sd;
        _n = n;
        _SD = FORMAT2D.format(_sd);
        _N = FORMAT2D.format(_n);

        double mu = 0; // Mean is always zero
        DiscreetValueMap pdist = WrappedNormalRandom.createProbabilityDensity(_n, _sd, mu, tolerance);
       _monteCarloRandom = new MonteCarloRandom(pdist, super.getRandom());
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
         super.initialise(strategyP, params, initialisationObjects);

        WrappedNormalAzimuthStrategy vms = (WrappedNormalAzimuthStrategy)AzimuthStrategyFactory.createAzimuthStrategy(strategyP, params, false);

        _sd = vms.getAngleOfTurnSD();
        _tolerance = vms.getTolerance();
        _SD = FORMAT2D.format(_sd);

        _n = vms.getResolutionN();
        _N = FORMAT2D.format(_n);

        double mu = 0; // Mean is always zero
        DiscreetValueMap pdist = WrappedNormalRandom.createProbabilityDensity(_n, _sd, mu, _tolerance);
       _monteCarloRandom = new MonteCarloRandom(pdist, super.getRandom());
    }

    public String getParameterSummary() {
        return "SD=" + _SD + ", N=" + _N + ", TOL=" + _tolerance;
    }

    public CourseChange generateCourseChange(double currentDirection) {
        return Geometry.createCourseChange(currentDirection, Math.toDegrees(_monteCarloRandom.nextDouble()));
    }

    public IAzimuthGenerator modifyAzimuthGenerator(VisualSignal visualSignal, double calculatedAzimuth) {
        return this;
//        int indexOfValue = visualSignal.getSignalInputs().findIndexOfKey(calculatedAzimuth, true);// can tell us what the signal strength of the direction we chose is if we like to use it...
//        double signalValue = visualSignal.getSignalInputs().getValue(indexOfValue);
//
////        if (log.isInfoEnabled()) {
////            log.info("Current Signal Value: " + signalValue);
////        }
//        IAzimuthGenerator generator = null;
//        boolean lessThanThreshold = DoubleMath.precisionLessThan(signalValue, super.getVisualSignalNoiseThreshold(), visualSignal.getSignalInputs().getPrecision());
//        if (lessThanThreshold) {
//            double psi = (_tolerance - _sd) / _tolerance;
//
//            double newk = _tolerance - super.calculateSignalNoise(_sd, psi, _tolerance);
//            generator = new WrappedCauchyAzimuthGenerator(super.getRandom(), newk, _n);
////            if (log.isInfoEnabled()) {
////                log.info("Adding noise k=" + newk);
////            }
//        } else {
//            generator = new FixedAzimuthGenerator(calculatedAzimuth);
//        }
//
//        return generator;
    }

    private static final Logger log = Logger.getLogger(WrappedNormalAzimuthGenerator.class);


    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _SD;
    private double _sd;
    private double _n;
    private String _N;
    private MonteCarloRandom _monteCarloRandom;
    private double _tolerance;
}
