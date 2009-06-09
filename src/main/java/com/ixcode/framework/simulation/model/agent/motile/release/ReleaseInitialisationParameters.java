/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.framework.math.DoubleMath;

import java.text.DecimalFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @todo remove X and Y from this - they are not relevant
 */
public class ReleaseInitialisationParameters {

    public ReleaseInitialisationParameters(double x, double y, double h, double d, double I) {
        _x = x;
        _y = y;
        _h = h;
        _d = d;
        _I = I;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public double getH() {
        return _h;
    }

    public double getD() {
        return _d;
    }

    public double getI() {
        return _I;
    }

    public String toString() {
        return "x=" + format(_x) +": y=" + format(_y) +  ": h=" + format(_h) + ": d=" + format(_d) + ": I="+ format(_I);
    }

    private String format(double x) {
        return F.format(x);
    }

    private double _x;
    private double _y;
    private double _h;
    private double _d;
    private double _I;

    private static final DecimalFormat F = DoubleMath.DECIMAL_FORMAT;
}
