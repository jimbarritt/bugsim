/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.action;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.view.landscape.action.CenterOnNextAgentAction;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculator;
import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class AddSomeAgents extends AbstractAction {

    public static final String ID = "Add Some Agents";

    public AddSomeAgents(Simulation simulation, CenterOnNextAgentAction centerOnAgentAction) {
        super(ID);
        _simulation = simulation;
        _centerOnAgentAction = centerOnAgentAction;
    }

    public void actionPerformed(ActionEvent actionEvent) {

        addCabbage(0, 10, 60, 9);
        addCabbage(1,20, 70, 9);
        addCabbage(2, 10, 70, 9);

        addCabbage(3, 50, 50, 9);
        addCabbage(4, 30, 75, 9);
        addCabbage(5, 40, 90, 9);
        addCabbage(6,90, 120, 9);
        addCabbage(7,110, 120, 9);
        addCabbage(8,100, 70, 9);

        addCabbage(9,50, 100, 9);
        addCabbage(10,30, 75, 9);
        addCabbage(11,40, 90, 9);

        addCabbage(12, 60, 30, 9);

        addCabbage(13, 150, 70, 9);
        addCabbage(14,80, 250, 9);

        // 0.01 they all get stuck on the first one they see, 0.001 they move past anything they meet to big patch.
        // 0.004 is about in the middle
        GravitationalCalculator gravitationalCalculator = new GravitationalCalculator(new ExponentialDecaySignalFunction(2, 1, .004), 0.000001);
//        GravitationalBugAgent bug = addGravityBug(200, 200, gravitationalCalculator);
//        _centerOnAgentAction.setAgent(bug);
//        addGravityBug(10, 150, gravitationalCalculator);
//        addGravityBug(0, 100, gravitationalCalculator);
//        addGravityBug(150, 0, gravitationalCalculator);

    }

//    private GravitationalBugAgent addGravityBug(double x, double y, GravitationalCalculator gravityMachine) {
//        GravitationalBugAgent bug = new GravitationalBugAgent(new Location(x, y), gravityMachine, 0, 10, 5);
//        _simulation.addAgent(bug);
//        return bug;
//    }

    private void addCabbage(long id, int x, int y, int radius) {
        CabbageAgent agent = new CabbageAgent(id, new Location(x, y), radius);
        _simulation.addAgent(agent);
    }
    private void addPitfallTrap(int x, int y, int radius) {
//        PitfallAgent agent = new PitfallAgent(new Location(x, y), radius);
//        _simulation.addAgent(agent);
    }


    private Simulation _simulation;
    private CenterOnNextAgentAction _centerOnAgentAction;
}
