/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.math.function.FunctionRegistry;
import com.ixcode.framework.swing.property.PropertyGroupPanel;

import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class FunctionChoicePanel extends PropertyGroupPanel {

    public static final String TITLE = "Choose Function";

    public FunctionChoicePanel(FunctionDisplayPanel displayPanel, FunctionRegistry functionRegistry) {
        super(TITLE);
        ForceFunctionComboBox functionCombo = new ForceFunctionComboBox(functionRegistry);
        functionCombo.setAction(new ApplyFunctionChangeAction(displayPanel, functionCombo));
        functionCombo.getAction().actionPerformed(new ActionEvent(displayPanel, 1, "init"));
        super.addCombo(functionCombo);
    }


}
