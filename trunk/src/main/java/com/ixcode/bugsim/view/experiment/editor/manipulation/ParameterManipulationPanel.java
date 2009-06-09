/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterManipulation;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.PropertyGroupPanel;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 8, 2007 @ 6:12:54 PM by jim
 */
public class ParameterManipulationPanel extends JPanel {
    public ParameterManipulationPanel(ParameterManipulation parameterManipulation) {
        super(new BorderLayout());

        initUI();
        setParameterManipulation(parameterManipulation);
    }

    private void setParameterManipulation(ParameterManipulation parameterManipulation) {
        _nameE.setValue(parameterManipulation.getParameter().getName());

        Object val= parameterManipulation.getValueToSet();
        String stringValue = "" + val;
        if (val instanceof Parameter) {
           stringValue = ((Parameter)val).getName(); 
        }
        _valueE.setValue(stringValue);
    }

    private void initUI() {
        PropertyGroupPanel container = new PropertyGroupPanel();

        _nameE = container.addPropertyEditor("name", "Parameter", 20, TextAlignment.LEFT, 100);
        _valueE = container.addPropertyEditor("value", "Value", 20, TextAlignment.LEFT, 100);


        super.add(container, BorderLayout.CENTER);
    }

    private ParameterManipulation _manip;
    private JLabel _label;
    private IPropertyValueEditor _nameE;
    private IPropertyValueEditor _valueE;
}
