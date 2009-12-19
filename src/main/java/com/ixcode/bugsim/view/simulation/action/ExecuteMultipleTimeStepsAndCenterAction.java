/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.action;

import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.view.simulation.SimulationControlPanel;
import com.ixcode.bugsim.view.landscape.action.CenterOnNextAgentAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExecuteMultipleTimeStepsAndCenterAction extends AbstractAction {

    /**
     * 
     */
    public static final String ID = "Execute Multiple Time Steps";

    public ExecuteMultipleTimeStepsAndCenterAction(ExecuteMultipleTimeStepsAction timeAction, CenterOnNextAgentAction centerAction) {
        super(ID);
        _timeAction = timeAction;
        _centerAction = centerAction;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        _timeAction.actionPerformed(actionEvent);
        _centerAction.actionPerformed(actionEvent);

    }

    private Simulation _simulation;
    private SimulationControlPanel _simulationControlPanel;
    private ExecuteMultipleTimeStepsAction _timeAction;
    private CenterOnNextAgentAction _centerAction;
}
