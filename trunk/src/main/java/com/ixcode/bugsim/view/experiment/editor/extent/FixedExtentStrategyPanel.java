/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.extent;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.FixedExtentStrategy;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
class FixedExtentStrategyPanel extends ExtentStrategyPanelBase {
    public FixedExtentStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, FixedExtentStrategy.class);
    }

    protected JPanel createStrategyPanel() {
        return  new JPanel();
    }



}
