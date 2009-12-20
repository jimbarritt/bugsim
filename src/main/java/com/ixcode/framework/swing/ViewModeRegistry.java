package com.ixcode.framework.swing;

import java.util.HashMap;
import java.util.Map;

public class ViewModeRegistry {

    private Map<ViewModeName, ViewMode> strategies = new HashMap<ViewModeName, ViewMode>();

    protected void addStrategy(ViewMode viewMode) {
        strategies.put(viewMode.getViewModeName(), viewMode);
    }

    public boolean hasMode(ViewModeName modeName) {
        return strategies.containsKey(modeName);
    }

    public ViewMode getViewMode(ViewModeName modeName) {
        if (!strategies.containsKey(modeName)) {
            throw new IllegalArgumentException("No strategy in registry " + this.getClass().getName() + " for " + modeName);
        }
        return strategies.get(modeName);
    }


}
