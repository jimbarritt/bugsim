/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AutoRotateMapAction extends ActionBase {

    public AutoRotateMapAction(MapImageView view) {
        super("Auto Rotate Map", "/icons/auto-rotate.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        MapOutline outline = _view.getMapOutline();
        double radians = outline.getAngleOfRotationRadians();

        _view.rotateMap(radians);
    }

    private MapImageView _view;    
}
