/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.ViewMode;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MapImageViewMode extends ViewMode {

    
    public static final MapImageViewMode EDIT_MAP_OUTLINE = new MapImageViewMode("edit-map-outline");

    private MapImageViewMode(String name) {
      super(name);
    }
}
