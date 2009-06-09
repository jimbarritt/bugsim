/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.signal.surface;

import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.SignalSurfaceStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 7, 2007 @ 5:35:09 PM by jim
 */
public class SignalSurfacePanel extends StrategyPanel {

    public SignalSurfacePanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("SignalSurfacePanel", modelAdapter, parameterMapLookup, SignalSurfaceStrategyFactory.getRegistry(), "Type", minWidth);
        addCards();
        super.setChangeTypeEnabled(false);
    }

    private void addCards() {
        FunctionalSignalSurfacePanel functionalSurfacePanel = new FunctionalSignalSurfacePanel(super.getModelAdapter(), super.getMinWidth());
        super.addStrategyDetailPanel(functionalSurfacePanel);
    }
}
