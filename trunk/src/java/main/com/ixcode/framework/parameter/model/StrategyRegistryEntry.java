/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 4:49:46 PM by jim
 */
public class StrategyRegistryEntry {
    public StrategyRegistryEntry(Class strategyClass, Class implementingClass, String strategyName, String strategyLabel) {
        _implementingClass = implementingClass;
        _strategyClass = strategyClass;
        _strategyName = strategyName;
        _strategyLabel = strategyLabel;
    }

    public Class getImplementingClass() {
        return _implementingClass;
    }

    public Class getStrategyClass() {
        return _strategyClass;
    }

    public String getStrategyName() {
        return _strategyName;
    }

    public String getStrategyLabel() {
        return _strategyLabel;
    }

    private Class _implementingClass;
    private Class _strategyClass;

    private String _strategyName;
    private String _strategyLabel;
}
