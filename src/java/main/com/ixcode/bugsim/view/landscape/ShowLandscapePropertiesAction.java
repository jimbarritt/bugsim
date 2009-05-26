/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.simulation.ControlFrame;
import com.ixcode.bugsim.view.simulation.SimulationControlPanel;
import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ShowLandscapePropertiesAction extends ActionBase {


    public ShowLandscapePropertiesAction(LandscapeView view) {
        super("Show properties", "/icons/add.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent e) {
        SimulationControlPanel controlPanel = new SimulationControlPanel(_view);
                ControlFrame cf = new ControlFrame(controlPanel);
                cf.setSize(400, 670);
        cf.show();

    }

    private LandscapeView _view;
}
