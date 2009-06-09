/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.Simulation;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.model.experiment.parameter.simulation.timescale.TimescaleStrategyFactory;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 28, 2007 @ 7:23:01 PM by jim
 */
public class TimescaleStrategyPanel extends StrategyPanel {

    public TimescaleStrategyPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("TimescaleStrategyPanel", modelAdapter, parameterMapLookup, TimescaleStrategyFactory.getRegistry(), "type", minWidth);
        addCards(modelAdapter, minWidth);
    }


    private void addCards(IModelAdapter modelAdapter, int minWidth) {
        ContinuousGenerationsPanel continuous = new ContinuousGenerationsPanel(modelAdapter, minWidth);
        DiscreteGenerationsPanel discreet = new DiscreteGenerationsPanel(modelAdapter, minWidth);

        super.addStrategyDetailPanel(continuous);
        super.addStrategyDetailPanel(discreet);

    }
}
