/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor.visual;

import com.ixcode.bugsim.experiment.parameter.forager.sensor.visual.VisualSensorStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 2:12:37 PM by jim
 */
public class VisualStrategyPanel extends StrategyPanel {

    public VisualStrategyPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("VisualStrategyPanel", modelAdapter, parameterMapLookup, VisualSensorStrategyFactory.getRegistry(), "Type", minWidth);
        addCards();
        super.setChangeTypeEnabled(false);
    }

    private void addCards() {
        FieldOfViewVisualStrategyPanel fovPanel = new FieldOfViewVisualStrategyPanel(super.getModelAdapter(), super.getMinWidth());
        super.addStrategyDetailPanel(fovPanel);
    }
}
