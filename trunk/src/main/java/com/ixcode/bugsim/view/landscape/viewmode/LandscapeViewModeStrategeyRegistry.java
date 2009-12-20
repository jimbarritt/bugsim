/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.ViewModeStrategyRegistry;
import com.ixcode.bugsim.view.landscape.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeViewModeStrategeyRegistry extends ViewModeStrategyRegistry {

    public LandscapeViewModeStrategeyRegistry(LandscapeView view) {
        addStrategy(new LandscapeDisplayMode(view));
        addStrategy(new AddAgentViewMode(view, null));
        addStrategy(new TestIntersectionViewMode(view));
    }
}
