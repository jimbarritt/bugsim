/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.strategy;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.IStrategyParameterBindingListener;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 28, 2007 @ 3:28:03 PM by jim
 */
public class StrategyDetailsPanel extends ParameterGroupPanel  implements IParameterMapLookup, IStrategyParameterBindingListener, IStrategyDetailsPanel {

    public StrategyDetailsPanel(IModelAdapter modelAdapter, Class strategyClass) {
        super(modelAdapter);
        _strategyClass = strategyClass;
    }
    public StrategyDetailsPanel(IModelAdapter modelAdapter, Class strategyClass, String title) {
        super(modelAdapter, title);
        _strategyClass = strategyClass;
    }

   public JComponent getDisplayComponent() {
       return this;
    }

    public String getStrategyClassName() {
        return _strategyClass.getName();
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        _strategy = strategyDefinition;
        super.setModel(_strategy);
    }

    public StrategyDefinition getStrategyDefinition() {
        return _strategy;
    }

    public Class getStrategyClass() {
        return _strategyClass;
    }

    public boolean hasStrategy() {
        return (_strategy != null);
    }

    public ParameterMap getParameterMap() {
            return _strategy == null ? null : _strategy.getParameterMap();
        }


    public void strategyChanged(StrategyDefinition oldValue, StrategyDefinition newValue) {

    }

    private Class _strategyClass;
    private StrategyDefinition _strategy;
}
