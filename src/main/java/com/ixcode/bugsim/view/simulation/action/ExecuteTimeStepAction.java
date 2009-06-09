/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.action;

import com.ixcode.framework.simulation.model.NullSimulationDisruptor;
import com.ixcode.framework.simulation.model.Simulation;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExecuteTimeStepAction extends AbstractAction {

    /**
     * 
     */
    public static final String ID = "Execute Time Step";

    public ExecuteTimeStepAction(Simulation simulation) {
        super(ID);
        _simulation = simulation;

    }

    public void actionPerformed(ActionEvent actionEvent) {
        _simulation.executeTimeStep(NullSimulationDisruptor.INSTANCE);

    }

    private Simulation _simulation;
}
