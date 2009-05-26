/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.signal.surface.function;

import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.function.GaussianSignalFunctionStrategy;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 7, 2007 @ 5:51:39 PM by jim
 */
public class GaussianSignalFunctionPanel extends StrategyDetailsPanel {

    public GaussianSignalFunctionPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, GaussianSignalFunctionStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        IPropertyValueEditor standardDeviationE = super.addParameterEditor(super.getStrategyClassName(), GaussianSignalFunctionStrategy.P_STANDARD_DEVIATION, minWidth);
        IPropertyValueEditor magnificationE = super.addParameterEditor(super.getStrategyClassName(), GaussianSignalFunctionStrategy.P_MAGNIFICATION, minWidth);

        super.addPropertyEditorBinding(standardDeviationE);
        super.addPropertyEditorBinding(magnificationE);
    }
}
