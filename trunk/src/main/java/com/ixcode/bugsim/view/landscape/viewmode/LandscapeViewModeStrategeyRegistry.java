/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.ViewModeStrategyRegistry;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.bugsim.view.landscape.action.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeViewModeStrategeyRegistry extends ViewModeStrategyRegistry {

    public LandscapeViewModeStrategeyRegistry(LandscapeView view, AgentTypeChoiceCombo combo) {
        addStrategy(new LandscapeDisplayModeStrategy(view));
        addStrategy(new AddAgentViewModeStrategy(view, combo));
        addStrategy(new TestIntersectionViewModeStrategy(view));
    }
}
