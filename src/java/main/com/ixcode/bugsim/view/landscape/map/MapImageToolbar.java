/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MapImageToolbar extends JToolBar {

    public MapImageToolbar(MapImageView view) {
        super.add(new OpenMapAction(view));
        super.add(new FlipVerticalAction(view));
        JToggleButton toggleButton = new JToggleButton(new EditMapOutlineAction(view));
        toggleButton.setText("");
        super.add(toggleButton);
        super.add(new AutoRotateMapAction(view));
        super.add(new CropMapAction(view));
        super.add(new SaveMapAction(view));
    }
}
