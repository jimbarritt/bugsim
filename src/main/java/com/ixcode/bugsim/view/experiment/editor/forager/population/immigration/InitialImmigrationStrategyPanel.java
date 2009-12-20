/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern.ImmigrationPatternPanel;
import com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern.DefaultImmigrationPatternStrategyDefinitionFactory;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:18:11 PM by jim
 */
public class InitialImmigrationStrategyPanel extends StrategyDetailsPanel {

    public InitialImmigrationStrategyPanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, InitialImmigrationStrategyDefinition.class);
        initUI(minWidth);

    }

    private void initUI(int minWidth) {
        JTabbedPane tabbedPane = new JTabbedPane();

        _patternPanel = new ImmigrationPatternPanel(super.getModelAdapter(), this, minWidth);
        _patternBinding = new StrategyParameterBinding("ImmigrationPattern", _patternPanel, DefaultImmigrationPatternStrategyDefinitionFactory.INSTANCE);
        tabbedPane.add("Immigration Pattern", _patternPanel);
        super.add(tabbedPane);
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

        _patternBinding.bind(strategyDefinition,  InitialImmigrationStrategyDefinition.P_IMMIGRATION_PATTERN);
    }

    private ImmigrationPatternPanel _patternPanel;
    private StrategyParameterBinding _patternBinding;
}

