/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ApplyFunctionChangeAction extends AbstractAction {

    public ApplyFunctionChangeAction(FunctionDisplayPanel displayPanel, ForceFunctionComboBox comboBox) {
        super("Apply");
        _displayPanel = displayPanel;
        _comboBox = comboBox;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        ISignalFunction function = _comboBox.getSelectedFunction();
        _displayPanel.setForceFunction(function);
    }

    private FunctionDisplayPanel _displayPanel;
    private ForceFunctionComboBox _comboBox;
}
