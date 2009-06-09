/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor.olfactory;

import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 2:12:37 PM by jim
 */
public class OlfactoryStrategyPanel extends StrategyPanel {

    public OlfactoryStrategyPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("OlfactoryStrategyPanel", modelAdapter, parameterMapLookup, OlfactorySensorStrategyFactory.getRegistry(), "Type", minWidth);
        addCards();
        super.setChangeTypeEnabled(false);
        super.setChangeTypeVisible(false);
    }

    private void addCards() {
        SignalSensorOlfactoryStrategyPanel ssPanel = new SignalSensorOlfactoryStrategyPanel(super.getModelAdapter(), 130);
        super.addStrategyDetailPanel(ssPanel);
    }
}
