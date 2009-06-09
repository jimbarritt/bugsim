/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IVisualAgent;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;

import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class VisualSignalProcessor {

    public VisualSignalProcessor(boolean enabled) {
        _visualInputTemplate = new DiscreetValueMap(0, 359, 1, DoubleMath.DOUBLE_PRECISION_DELTA);
        _enabled = enabled;
    }


    /**
     * Go through each agent and build up a map of the signals - if get 2 signals from same directino
     * they sum together.
     *
     * @param agent
     * @param agentsInFOV
     * @return
     */
    public VisualSignal processVisualSignalInputs(IMotileAgent agent) {
        VisualSignal  signal = new VisualSignal(false, _visualInputTemplate, 0, 0);
        if (_enabled) {
            signal = calculateVisualSignal(agent);
        }
        return signal;
    }

    private VisualSignal calculateVisualSignal(IMotileAgent agent) {
        IVisualAgent viewer = getVisualAgent(agent);

        // @todo could pass in the filter here - mught have more than cabbage agents
        List agentsInFOV = viewer.getVisionStrategy().getVisibleAgents();

        DiscreetValueMap inputs = _visualInputTemplate.copyStructure();
        for (Iterator itr = agentsInFOV.iterator(); itr.hasNext();) {
            IPhysicalAgent observedAgent = (IPhysicalAgent)itr.next();
            AzimuthCoordinate ac = calculateDisplacementToAgent(agent, observedAgent);
            double inputValue = calculateVisualSignalInput(ac.getDistance(), observedAgent, viewer);
            double theta = ac.getAzimuth();


            int i = inputs.findIndexOfKey(theta, true);
            inputs.setValue(i, inputs.getValue(i) + inputValue);
        }

        double maxVisualSignalStrength = inputs.maxOfValues();
        double totalSignal = inputs.sumOfValues();
        boolean isReceivingSignal = receivingSignal(viewer, maxVisualSignalStrength, inputs.getPrecision());
        return new VisualSignal(isReceivingSignal, inputs, maxVisualSignalStrength, totalSignal);
    }

    private IVisualAgent getVisualAgent(IMotileAgent agent) {
        if (!(agent instanceof IVisualAgent)) {
            throw new IllegalStateException("Agent must implement " + IVisualAgent.class.getName() + ", to be used with " + VisualSignalProcessor.class.getName());
        }
        return (IVisualAgent)agent;
    }

    /**
     * See chapter on sensory perception eq. 2.1.21)
     * for the moment the signal input depends entirely on distance. If you set it up right you can even remove this
     * factor so that all inputs within range will have a signal of 1 (Simply set gamma = 0 and luminosity will always be 1)
     *
     * @param distance
     * @param agent
     * @return
     */
    private double calculateVisualSignalInput(double distance, IPhysicalAgent agent, IVisualAgent viewer) {
        double gamma = viewer.getVisionStrategy().getLuminosityGamma();
        double lambda = calculateLuminosityFactor(distance, gamma); // @todo hmmm methinks this could all be done in the viewer , eh? 
        double alpha = viewer.getVisionStrategy().calculateApparency(agent);
        return lambda * alpha;
    }


    private double calculateLuminosityFactor(double distance, double gamma) {
        return Math.exp(-gamma * distance);
    }

    private AzimuthCoordinate calculateDisplacementToAgent(IMotileAgent thisAgent, IPhysicalAgent agent) {
        return thisAgent.getLocation().getCoordinate().calculateAzimuthCoordinateTo(agent.getLocation().getCoordinate());
    }

    private boolean receivingSignal(IVisualAgent visualAgent, double maxVisualSignalStrength, double precisionDelta) {
        double visualSignalThreshold = visualAgent.getVisionStrategy().getVisualSignalThreshold();
        return (maxVisualSignalStrength > 0 && DoubleMath.precisionGreaterThanEqual(maxVisualSignalStrength, visualSignalThreshold, precisionDelta));
    }

    public boolean isEnabled() {
        return _enabled;
    }


    private DiscreetValueMap _visualInputTemplate;

    private boolean _enabled;
}
