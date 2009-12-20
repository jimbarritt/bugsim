/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.map;

import com.ixcode.framework.swing.DisplayMode;
import com.ixcode.framework.swing.ViewModeStrategyRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MapImageViewModeStrategeyRegistry extends ViewModeStrategyRegistry {

    public MapImageViewModeStrategeyRegistry(MapImageView view) {
        addStrategy(new DisplayMode());
        addStrategy(new EditMapOutlineMode(view));
    }
}