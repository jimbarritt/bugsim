/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.framework.math.DiscreetValueMap;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 13, 2007 @ 11:59:59 AM by jim
 */
public class VisualSignal {

    public VisualSignal(boolean recievingSignal, DiscreetValueMap signalInputs, double maxSignal, double totalSignal) {
        _recievingSignal = recievingSignal;
        _signalInputs = signalInputs;
        _maxSignal = maxSignal;
        _totalSignal = totalSignal;
    }

    public boolean isRecievingSignal() {
        return _recievingSignal;
    }

    public DiscreetValueMap getSignalInputs() {
        return _signalInputs;
    }

    public double getMaxSignal() {
        return _maxSignal;
    }

    public double getTotalSignal() {
        return _totalSignal;
    }

    private boolean _recievingSignal;
    private DiscreetValueMap _signalInputs;
    private double _maxSignal;
    private double _totalSignal;
    
}
