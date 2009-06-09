/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EditMapOutlineAction extends ActionBase {

    public EditMapOutlineAction(MapImageView view) {
        super("Edit Map Outline", "/icons/edit-map-medium.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (!_view.isMode(MapImageViewMode.EDIT_MAP_OUTLINE)) {
            _view.setViewMode(MapImageViewMode.EDIT_MAP_OUTLINE);
            _view.resetMapOutline();
        } else {
            _view.setViewMode(MapImageViewMode.DISPLAY);
        }
    }

    private MapImageView _view;
}
