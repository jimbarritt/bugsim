/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.action;

import com.ixcode.framework.simulation.model.Simulation;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CleanSimulationAction extends AbstractAction {

    /**
     * @todo create base class which accepts both an id and a caption.
     */
    public static final String ID = "Clean Simulation";

    public CleanSimulationAction(Simulation simulation) {
        super(CleanSimulationAction.ID);
        _simulation = simulation;

    }

    public void actionPerformed(ActionEvent actionEvent) {
        _simulation.clean();
      
    }


    private Simulation _simulation;
}
