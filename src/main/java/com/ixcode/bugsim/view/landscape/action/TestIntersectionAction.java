/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.framework.swing.action.ActionBase;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.bugsim.view.landscape.viewmode.*;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class TestIntersectionAction extends ActionBase {

    public TestIntersectionAction(LandscapeView view) {
        super("Test Intersection", "/icons/add.gif");
        _view = view;
        
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (!_view.isViewMode(LandscapeViewMode.TEST_INTERSECTION)) {
            _view.setViewMode(LandscapeViewMode.TEST_INTERSECTION);

        } else {
            _view.setViewMode(LandscapeViewMode.DISPLAY);
        }
    }

    private LandscapeView _view;

}
