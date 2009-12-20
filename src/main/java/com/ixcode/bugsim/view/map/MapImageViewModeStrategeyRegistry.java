/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.map;

import com.ixcode.framework.swing.DisplayMode;
import com.ixcode.framework.swing.ViewModeRegistry;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MapImageViewModeStrategeyRegistry extends ViewModeRegistry {

    public MapImageViewModeStrategeyRegistry(MapImageView view) {
        addStrategy(new DisplayMode());
        addStrategy(new EditMapOutlineMode(view));
    }
}
