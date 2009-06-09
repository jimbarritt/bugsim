/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.action;

import com.ixcode.framework.simulation.model.NullSimulationDisruptor;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.view.simulation.SimulationControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExecuteMultipleTimeStepsAction extends AbstractAction {

    /**
     * 
     */
    public static final String ID = "Execute Multiple Time Steps";

    public ExecuteMultipleTimeStepsAction(Simulation simulation, SimulationControlPanel controlPanel) {
        super(ID);
        _simulation = simulation;
        _simulationControlPanel = controlPanel;

    }

    public void actionPerformed(ActionEvent actionEvent) {
        for (int i=0;i<_simulationControlPanel.getNumberOfTimeSteps();++i)  {
            _simulation.executeTimeStep(NullSimulationDisruptor.INSTANCE);

        }
        System.out.println("Execution of " + _simulationControlPanel.getNumberOfTimeSteps() + " timesteps complete.");

    }

    private Simulation _simulation;
    private SimulationControlPanel _simulationControlPanel;
}
