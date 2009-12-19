/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.ViewMode;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeViewMode extends ViewMode {


    public static final LandscapeViewMode ADD_AGENT = new LandscapeViewMode("add-agent");
    public static final ViewMode TEST_INTERSECTION = new LandscapeViewMode("test-intersection");;
    private LandscapeViewMode(String name) {
        super(name);
    }



}
