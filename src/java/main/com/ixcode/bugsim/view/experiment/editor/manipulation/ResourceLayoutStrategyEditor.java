/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceLayoutStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.ResourceLayoutPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.layout.DefaultLayoutStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 14, 2007 @ 9:35:02 PM by jim
 */
public class ResourceLayoutStrategyEditor extends JPanel implements IStrategyEditor{
    public ResourceLayoutStrategyEditor(IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        super(new BorderLayout());

        _lookup = lookup;
        _layoutPanel = new ResourceLayoutPanel(modelAdapter, lookup);
        _layoutPanel.setChangeTypeEnabled(false);
        super.add(_layoutPanel, BorderLayout.CENTER);
    }

    public JComponent getEditingComponent() {
        return this;
    }

    public void setStrategyDefinitionParameter(StrategyDefinitionParameter strategyS) {

        String strategyClassName = ResourceLayoutStrategyFactory.getRegistry().getStrategyClassForImplementingClass(strategyS.getImplementingClassName()).getName();
        if (strategyS.isParameterManipulation()) {
            _sd = ResourceLayoutStrategyFactory.getRegistry().constructStrategy(strategyS, strategyS.getParameterMap(), true);            
        } else {
            _sd = DefaultLayoutStrategyFactory.INSTANCE.createDefaultStrategyDefinition(strategyClassName, _lookup);
        }

//        _sd= ResourceLayoutStrategyFactory.getRegistry().createDefaultStrategy(strategyClassName, strategyS.getParameterMap());
//        _sd = ResourceLayoutStrategyFactory.getRegistry().constructStrategy(strategyS, strategyS.getParameterMap(), true);

        // need to hook up binding ...
        _layoutPanel.setStrategyDefinition(_sd);


    }

    public StrategyDefinitionParameter getStrategyDefinitionParameter() {
        return _sd.getStrategyS();
    }


    private JLabel _label = new JLabel();
    private StrategyDefinition _sd;
    private ResourceLayoutPanel _layoutPanel;
    private StrategyDefinitionParameter _originalStrategyS;
    private IParameterMapLookup _lookup;
}
