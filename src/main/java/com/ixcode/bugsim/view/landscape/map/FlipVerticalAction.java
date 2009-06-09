/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class FlipVerticalAction extends ActionBase {

    public FlipVerticalAction(MapImageView view) {
        super("Flip Vertical", "/icons/flip-vertical.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent actionEvent) {
       _view.flipRawImageVertical();
    }

    private MapImageView _view;
}
