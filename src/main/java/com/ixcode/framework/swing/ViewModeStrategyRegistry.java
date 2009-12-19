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

    protected void addStrategy(ViewModeStrategy strategy) {
        _strategies.put(strategy.getViewMode(), strategy);
    }

    public ViewModeStrategy getStrategy(ViewMode mode) {
        if (!_strategies.containsKey(mode))  {
            throw new IllegalArgumentException("No strategy in registry " + this.getClass().getName() + " for " + mode);
        }
        return (ViewModeStrategy)_strategies.get(mode);
    }

    private Map _strategies = new HashMap();
}
