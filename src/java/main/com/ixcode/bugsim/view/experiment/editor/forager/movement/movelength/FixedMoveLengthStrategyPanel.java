/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.movelength;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.movelength.FixedMoveLengthStrategy;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:04:44 PM by jim
 */
public class FixedMoveLengthStrategyPanel extends StrategyDetailsPanel  {

    public FixedMoveLengthStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, FixedMoveLengthStrategy.class);
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());

        IPropertyValueEditor moveLength = super.addParameterEditor(super.getStrategyClassName(), FixedMoveLengthStrategy.P_MOVE_LENGTH, 120);


        super.addPropertyEditorBinding(moveLength);

        super.add(container,  BorderLayout.CENTER);
    }
}
