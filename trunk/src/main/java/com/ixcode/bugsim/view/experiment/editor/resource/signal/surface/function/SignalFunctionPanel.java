/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.signal.surface.function;

import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function.SignalFunctionStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 7, 2007 @ 5:48:48 PM by jim
 */
public class SignalFunctionPanel extends StrategyPanel {

    public SignalFunctionPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("SignalFunctionPanel", modelAdapter, parameterMapLookup, SignalFunctionStrategyFactory.getRegistry(), "Type", minWidth);
        addCards();
        super.setChangeTypeEnabled(false);
    }

    private void addCards() {
        GaussianSignalFunctionPanel gaussianPanel = new GaussianSignalFunctionPanel(super.getModelAdapter(), super.getMinWidth());
        super.addStrategyDetailPanel(gaussianPanel);
    }
}
