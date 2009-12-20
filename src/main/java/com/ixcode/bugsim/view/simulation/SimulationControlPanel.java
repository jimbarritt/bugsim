/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation;

import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.view.landscape.properties.LandscapePropertiesPanel;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.bugsim.view.landscape.properties.LandscapeViewPropertiesPanel;
import com.ixcode.bugsim.view.landscape.action.CenterOnNextAgentAction;
import com.ixcode.bugsim.view.simulation.action.ExecuteMultipleTimeStepsAction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimulationControlPanel extends JPanel {


    public SimulationControlPanel(LandscapeView view) {
        _view = view;
        _landscape = view.getLandscape();
//        setSimulation(view.getSimulation());

    }

    public void setSimulation(Simulation simulation) {
          List simulationActions = new ArrayList();
        super.removeAll();

        CenterOnNextAgentAction centerOnAgentAction = new CenterOnNextAgentAction(_view);
        ExecuteMultipleTimeStepsAction timeAction = new ExecuteMultipleTimeStepsAction(simulation, this);

//        simulationActions.add(new AddSomeAgents(simulation, centerOnAgentAction));
//        simulationActions.add(new ResetSimulationAction(simulation));
//        simulationActions.add(new CleanSimulationAction(simulation));
//        simulationActions.add(new ExecuteTimeStepAction(simulation));
//        simulationActions.add(timeAction);
//        simulationActions.add(centerOnAgentAction);
//        simulationActions.add(new ExecuteMultipleTimeStepsAndCenterAction(timeAction, centerOnAgentAction));



        setLayout(new BoxLayout(this, 3));
        LandscapePropertiesPanel p = new LandscapePropertiesPanel(_landscape);
        super.add(p);
        LandscapeViewPropertiesPanel pviewer = new LandscapeViewPropertiesPanel(_view);
        super.add(pviewer);
//        LandscapeViewPropertiesPanel pZoomviewer = new LandscapeViewPropertiesPanel(zoomView);
//        add(pZoomviewer);
//        _pActions = new SimulationActionsPanel(simulationActions);
//        super.add(_pActions);
    }

    public Landscape getLandscape() {
        return _landscape;
    }

    public int getNumberOfTimeSteps() {
        return _pActions.getNumberOfTimeSteps();
    }




    private Landscape _landscape;
    SimulationActionsPanel _pActions;
    private LandscapeView _view;
}
