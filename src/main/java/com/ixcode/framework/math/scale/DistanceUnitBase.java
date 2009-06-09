/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class DistanceUnitBase implements IDistanceUnit {

    protected DistanceUnitBase(String name, String symbol) {
        _name = name;
        _symbol = symbol;
    }

    public String getName() {
        return _name;
    }

    public String getSymbol() {
        return _symbol;
    }

    public double convertValue(IDistanceUnit distanceUnit, double value) {
        double scale = getScale(distanceUnit);
        return value * scale;
    }

    private double getScale(IDistanceUnit distanceUnit) {
        return ((Double)_scale.get(distanceUnit.getClass().getName())).doubleValue();
    }

    protected void addScale(Class unitClass,  double scale) {
        _scale.put(unitClass.getName(), new Double(scale));
    }

    private Map _scale = new HashMap();

    private String _name;
    private String _symbol;
}
