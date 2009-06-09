/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.MultipleParameterManipulation;
import com.ixcode.framework.parameter.model.ParameterManipulation;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 8, 2007 @ 6:11:33 PM by jim
 */
public class ManipulationSequenceCellRenderer implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component comp = null;
        if (value instanceof ParameterManipulation) {
            comp = new ParameterManipulationPanel((ParameterManipulation)value);
        } else if (value instanceof MultipleParameterManipulation) {
            comp = new MultipleParameterManipulationPanel((MultipleParameterManipulation)value);
        } else {
            comp = new JLabel("" + value);
        }
        return createSelectionContainer(comp, isSelected, cellHasFocus);

    }

    private Component createSelectionContainer(Component comp, boolean selected, boolean cellHasFocus) {
        JPanel container = new JPanel(new BorderLayout());


//        JPanel buttonContainer = new JPanel(new BorderLayout());

//        buttonContainer.add(Box.createHorizontalStrut(5), BorderLayout.NORTH);


        JToggleButton selectedButton = new JToggleButton("");
        int width = 20;
        selectedButton.setPreferredSize(new Dimension(width, 0));
        selectedButton.setMaximumSize(new Dimension(width, 1000));
        selectedButton.setMinimumSize(new Dimension(width, 0));
        selectedButton.setSize(width, 10);
        selectedButton.setSelected(selected);
        container.add(selectedButton, BorderLayout.WEST);

//        container.add(buttonContainer,  BorderLayout.WEST);
        container.add(comp, BorderLayout.CENTER);

        container.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return container;

    }
}
