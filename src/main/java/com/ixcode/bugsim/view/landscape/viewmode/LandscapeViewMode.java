package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.*;

public class LandscapeViewMode extends ViewModeName {
    
    public static final LandscapeViewMode ADD_AGENT = new LandscapeViewMode("add-agent");
    public static final ViewModeName TEST_INTERSECTION = new LandscapeViewMode("test-intersection");

    private LandscapeViewMode(String name) {
        super(name);
    }

}
