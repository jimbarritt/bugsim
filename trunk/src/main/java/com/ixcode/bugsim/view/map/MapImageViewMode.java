/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.map;

import com.ixcode.framework.swing.ViewModeName;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MapImageViewMode extends ViewModeName {

    
    public static final MapImageViewMode EDIT_MAP_OUTLINE = new MapImageViewMode("edit-map-outline");

    private MapImageViewMode(String name) {
      super(name);
    }
}
