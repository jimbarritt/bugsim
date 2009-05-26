/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CropMapAction extends ActionBase {

    public CropMapAction(MapImageView view) {
        super("Crop Map", "/icons/crop-map.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (_view.getMapOutline() != null) {
        Rectangle2D cropBounds = _view.getMapOutline().getBounds2D();
        _view.cropRawImage(cropBounds);
        }
    }

    private MapImageView _view;

}
