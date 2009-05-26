/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.MultipleParameterManipulation;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 8, 2007 @ 6:14:42 PM by jim
 */
public class MultipleParameterManipulationPanel extends JPanel {
    public MultipleParameterManipulationPanel(MultipleParameterManipulation multipleParameterManipulation) {
           super(new BorderLayout());

        initUI();
        setParameterManipulation(multipleParameterManipulation);
    }

    private void setParameterManipulation(MultipleParameterManipulation parameterManipulation) {
        _label.setText("Multiple Params: " + parameterManipulation.getManipulations().size());
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());
        _label = new JLabel("Parameter");
        container.add(_label, BorderLayout.NORTH);

        super.add(container, BorderLayout.CENTER);
    }

    private MultipleParameterManipulation _manip;
    private JLabel _label;
}
