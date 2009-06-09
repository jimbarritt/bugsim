/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimulationActionsPanel extends JPanel {
    public int getNumberOfTimeSteps() {
        return Integer.parseInt(_numberOfTimestepsTextField.getText());
    }

    public static final String TITLE = "Simulation Actions";


    public SimulationActionsPanel(List simulationActions) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JPanel north = new JPanel(new BorderLayout());
        north.add(new JLabel(TITLE), BorderLayout.NORTH);
        super.add(north);


        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));

        for (Iterator itr = simulationActions.iterator(); itr.hasNext();) {
            Action action = (Action)itr.next();

            JPanel actionPanel = new JPanel(new BorderLayout());
            actionPanel.add(new JButton(action), BorderLayout.NORTH);
            actionsPanel.add(actionPanel);

        }

        add(_numberOfTimestepsTextField);
        add(actionsPanel);


//        JPanel south = new JPanel(new BorderLayout());
//        south.add(new JButton("hello"), BorderLayout.SOUTH);
//        super.add(south);

//




    }

    private JTextField _numberOfTimestepsTextField = new JTextField("120");
}
