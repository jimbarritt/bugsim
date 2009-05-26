/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.extent;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.ProportionalExtentStrategy;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
class ProportionalExtentStrategyPanel extends ExtentStrategyPanelBase {

    public ProportionalExtentStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, ProportionalExtentStrategy.class);
    }

    protected JPanel createStrategyPanel() {
        JPanel p = new JPanel();


        p.add(new JLabel("Proportional ERxtent Properties")) ;
        return p;
    }


}
