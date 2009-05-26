/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import java.text.DecimalFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 13, 2007 @ 12:00:13 PM by jim
 */
public class OlfactorySignal {

    public OlfactorySignal(boolean receivingSignal, double signalDelta, double azimuthDelta, double signalTotal, double signalLeft, double signalRight, double signalProportion) {
        _receivingSignal = receivingSignal;
        _signalDelta = signalDelta;
        _azimuthDelta = azimuthDelta;
        _signalTotal = signalTotal;
        _signalLeft = signalLeft;
        _signalRight = signalRight;
        _signalProportion = signalProportion;
    }

    public boolean isReceivingSignal() {
        return _receivingSignal;
    }

    public double getSignalDelta() {
        return _signalDelta;
    }

    public double getAzimuthDelta() {
        return _azimuthDelta;
    }

    public double getSignalTotal() {
        return _signalTotal;
    }

    public double getSignalLeft() {
        return _signalLeft;
    }

    public double getSignalRight() {
        return _signalRight;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("rcv=").append(_receivingSignal);
        sb.append(", sL=").append(F.format(_signalLeft));
        sb.append(", sR=").append(F.format(_signalRight));
        sb.append(", sT=").append(F.format(_signalTotal));
        sb.append(", sD=").append(F.format(_signalDelta));
        sb.append(", sPr=").append(F.format(_signalProportion));
        sb.append(", azD=").append(F.format(_azimuthDelta));


        return sb.toString();
    }
    private boolean _receivingSignal;
    private double _signalDelta;
    private double _azimuthDelta;
    private double _signalTotal;
    private double _signalLeft;
    private double _signalRight;

    private static final DecimalFormat F =new DecimalFormat("0.00000");

    public static final OlfactorySignal NO_SIGNAL = new OlfactorySignal(false, 0, 0, 0, 0, 0, 0);
    private double _signalProportion;
}
