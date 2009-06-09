/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DistanceUnitRegistry {
    public static IDistanceUnit metres() {
        return INSTANCE.resolveUnit(Metres.class.getName());
    }

    public static IDistanceUnit centimetres() {
        return INSTANCE.resolveUnit(Centimetres.class.getName());
    }

    public static final DistanceUnitRegistry INSTANCE = new DistanceUnitRegistry();

    private DistanceUnitRegistry() {
        addDistanceUnit(new Millimetres());
        addDistanceUnit(new Centimetres());
        addDistanceUnit(new Metres());
        addDistanceUnit(new Kilometres());
    }

    private void addDistanceUnit(IDistanceUnit unit) {
        _registry.put(unit.getClass().getName(), unit);
        _index.add(unit);
        _symbolIndex.put(unit.getSymbol(), unit);
    }

    public IDistanceUnit resolveUnit(String distanceUnitClassName) {
        return (IDistanceUnit)_registry.get(distanceUnitClassName);


    }

    public IDistanceUnit resolveUnitFromSymbol(String symbol) {
        return (IDistanceUnit)_symbolIndex.get(symbol);
    }


    public List getDistanceUnits() {
        return _index;
    }

    private Map _registry = new HashMap();
    private List _index = new ArrayList();
    private Map _symbolIndex = new HashMap();
    public static final ScaledDistance SCALE_ONE_CM = new ScaledDistance(1, DistanceUnitRegistry.centimetres());
    public static final ScaledDistance SCALE_ONE_M = new ScaledDistance(1, DistanceUnitRegistry.metres());;
}
