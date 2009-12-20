/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.bugsim.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IOlfactoryAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class OlfactorySignalProcessor {

    public OlfactorySignalProcessor(boolean enabled) {
        _enabled = enabled;
    }

    /**
     * Note we do not include sensors which are EXACTLY at 0 or 180 - can ignore a sensor at 0 but dont
     * know what to do with one at 180 - maybe randomly assign it to a side ?
     * // For all sensors where 0 < theta < 180 sum up the signal
     * // For all sensors where 180 < theta < 360 sum up the signal
     *
     * @param agent
     * @return
     */
    public OlfactorySignal processOlfactorySignals(IOlfactoryAgent agent, Landscape landscape) {
        OlfactorySignal olfactorySignal = OlfactorySignal.NO_SIGNAL;
        if (_enabled) {
            olfactorySignal = calculateSignal(agent, landscape);
        }
        return olfactorySignal;
    }

    /**
     * See
     * @param agent
     * @param landscape
     * @return
     */
    public OlfactorySignal calculateSignal(IOlfactoryAgent agent, Landscape landscape) {
        SignalSensorOlfactoryStrategy strategy = getSignalSensorStrategy(agent);
        List sensors = strategy.getSensors();

        List leftSensors = new ArrayList();
        List rightSensors = new ArrayList();

        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();
            double h = sensor.getHeadingFromAgent();
            if (DoubleMath.precisionBetweenExclusive(0, h, 180, DoubleMath.DOUBLE_PRECISION_DELTA)) {
                rightSensors.add(sensor);
            } else if (DoubleMath.precisionBetweenExclusive(180,  h, 360, DoubleMath.DOUBLE_PRECISION_DELTA)) {
                leftSensors.add(sensor);
            } else { // must be 0 or 180
                throw new IllegalStateException("Cannot yet handle a sensor at: " + h + " degrees from agent!");
            }
        }

        double signalSumLeft = sumOfSignal((IMotileAgent)agent, landscape, leftSensors);
        double signalSumRight = sumOfSignal((IMotileAgent)agent, landscape, rightSensors);
        double signalTotal = signalSumLeft + signalSumRight;

        boolean recievingSignal = (signalTotal > 0);

        OlfactorySignal signal = OlfactorySignal.NO_SIGNAL;
        if (recievingSignal) {
            double signalDelta = (signalSumRight - signalSumLeft) * strategy.getSignalDeltaSensitivity();

                       
            double signalProportion = (signalDelta>signalTotal) ? 1 : signalDelta / signalTotal;

            double directionDeltaMax = strategy.getDirectionDeltaMax();
            double azimuthDelta = transformToAzimuthDelta(signalProportion, directionDeltaMax);
            signal = new OlfactorySignal(true, signalDelta, azimuthDelta, signalTotal, signalSumLeft, signalSumRight, signalProportion);
        }

        return signal;
    }

    private double sumOfSignal(IMotileAgent agent, Landscape landscape, List sensors) {
        double sum = 0;
        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();
            sum += sensor.getInformationSample(agent, landscape).getDoubleSignalValue();
        }
        return sum;
    }

    /**
     * // take the difference and transfrom it into a change in azimuth via tan function, taking account of maximum turn possible.
     * divide by tan(1) to get a proportion which we can then multiply by the maxDelta
     * @return
     */
    private double transformToAzimuthDelta(double signalProportion, double directionDeltaMax) {
        return directionDeltaMax * ((Math.tan(signalProportion)) / TAN_ONE);
    }

    private SignalSensorOlfactoryStrategy getSignalSensorStrategy(IOlfactoryAgent agent) {
        if (!(agent.getOlfactionStrategy() instanceof SignalSensorOlfactoryStrategy)) {
            throw new IllegalStateException("Agent must have olfactory strategy of " + SignalSensorOlfactoryStrategy.class.getName());
        }
        return (SignalSensorOlfactoryStrategy)agent.getOlfactionStrategy();
    }

    public boolean isEnabled() {
        return _enabled;
    }


    private boolean _enabled;

    private static final double TAN_ONE = Math.tan(1);
}
