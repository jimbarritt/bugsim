/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ViewModeStrategyRegistry {


    public ViewModeStrategyRegistry() {


    }

    protected void addStrategy(ViewMode strategy) {
        _strategies.put(strategy.getName(), strategy);
    }

    public ViewMode getStrategy(ViewModeName modeName) {
        if (!_strategies.containsKey(modeName))  {
            throw new IllegalArgumentException("No strategy in registry " + this.getClass().getName() + " for " + modeName);
        }
        return (ViewMode)_strategies.get(modeName);
    }

    private Map _strategies = new HashMap();
}
