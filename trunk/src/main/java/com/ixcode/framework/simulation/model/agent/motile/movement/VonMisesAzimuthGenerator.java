/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.random.VonMisesRandomCain;
import com.ixcode.framework.math.random.IRandom;
import com.ixcode.framework.math.random.VonMisesRandomCircStats;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignal;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;

import java.util.Map;
import java.util.Random;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 *  Description : Generates Random Headings using a von mises distribution.
 */
public class VonMisesAzimuthGenerator extends AzimuthGeneratorBase {



    public VonMisesAzimuthGenerator() {
    }

    public VonMisesAzimuthGenerator(Random random, double k, double n, boolean useCains) {
        super(random);
        _k = k;
        _n = n;
        _K = FORMAT2D.format(_k);
        _N = FORMAT2D.format(_n);

        double mu = 0; // Mean is always zero
        if (useCains) {
            _random = new VonMisesRandomCain(super.getRandom(), mu, _k, _n);
        } else {
            _random = new VonMisesRandomCircStats(super.getRandom(), mu, _k);

        }
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
         super.initialise(strategyP, params, initialisationObjects);

        VonMisesAzimuthStrategy vms = (VonMisesAzimuthStrategy)AzimuthStrategyFactory.createAzimuthStrategy(strategyP, params, false);

        _k= vms.getAngleOfTurnK();
        _maxK = vms.getMaxK();
        _K = FORMAT2D.format(_k);

        _n = vms.getResolutionN();
        _N = FORMAT2D.format(_n);

        boolean useCains = vms.isUseCainMethod();
        double mu = 0; // Mean is always zero
        if (useCains) {
            _random = new VonMisesRandomCain(super.getRandom(), mu, _k, _n);
        } else {
            _random = new VonMisesRandomCircStats(super.getRandom(), mu, _k);

        }

    }

    public String getParameterSummary() {
        return "K=" + _K + ", N=" + _N;
    }

    /**
     * THere is actually a bug here, this should use the Geometry.createCourseChange function which expects a
     * change in direction rather than a new azimuth... however because we first convert it into polar coordinate it doesnt make any difference.
     *
     * Secondly, the CAIN generator DOES return numbers as Azimuths... AAARGH so for IT it is the correct usage... we need
     * to replace it with if (!CAIN) do the other one...
     *
     * anyway not going to change it now.
     * @param currentDirection
     * @return
     */
    public CourseChange generateCourseChange(double currentDirection) {
        double thetaRadians= _random.nextDouble();
        return Geometry.createCourseChangeFromNewAzimuth(currentDirection, Math.toDegrees(thetaRadians));
    }

    /**
     * Adjusts the width of the angle of turn -
     * @param visualSignal
     * @param calculatedAzimuth
     * @return
     *
     * @todo - this shouldnt even be in here!!
     */
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
            generator = new VonMisesAzimuthGenerator(super.getRandom(), newk, _n, true);
//            if (log.isInfoEnabled()) {
//                log.info("Adding noise k=" + newk);
//            }
            throw new IllegalStateException("This is all wrong!refer to chapter 03- simulation framework vision section - theres a box which explains it");
        } else {
            generator = new FixedAzimuthGenerator(calculatedAzimuth);
        }

        return generator;
    }

    private static final Logger log = Logger.getLogger(VonMisesAzimuthGenerator.class);


    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _K;
    private double _k;
    private double _n;
    private String _N;
    private IRandom _random;
    private double _maxK;
    private double _arcLength;
}
