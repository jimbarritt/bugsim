package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.*;

public class LandscapeViewModeRegistry extends ViewModeRegistry {

    public LandscapeViewModeRegistry() {
        addStrategy(new LandscapeDisplayMode());
        addStrategy(new AddAgentViewMode(null));
        addStrategy(new TestIntersectionViewMode());
    }


}
