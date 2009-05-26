/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.signal;

import com.ixcode.bugsim.model.experiment.parameter.resource.signal.SignalStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : 
 *  Created     : Jan 25, 2007 @ 4:32:32 PM by jim
 */
public class ResourceSignalPanel extends StrategyPanel {

    public ResourceSignalPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("ResourcesignalPanel", modelAdapter, parameterMapLookup, SignalStrategyFactory.getRegistry(), "Type", minWidth);
        super.setChangeTypeEnabled(false);

        addCards();
    }

    private void addCards() {
        MultipleSurfaceSignalStrategyPanel ssp = new MultipleSurfaceSignalStrategyPanel(super.getModelAdapter(), super.getMinWidth());
        super.addStrategyDetailPanel(ssp);

    }



}
