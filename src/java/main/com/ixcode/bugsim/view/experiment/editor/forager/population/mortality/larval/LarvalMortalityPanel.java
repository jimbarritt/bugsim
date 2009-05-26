/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.larval;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LarvalMortalityStrategyFactory;

import javax.swing.*;

import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 5, 2007 @ 8:01:33 PM by jim
 */
public class LarvalMortalityPanel extends StrategyPanel {

    public LarvalMortalityPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("Larval Mortality Panel", modelAdapter, parameterMapLookup, LarvalMortalityStrategyFactory.getRegistry(), "Type", minWidth);
        addCards(minWidth);
        super.setChangeTypeEnabled(false);
    }

    private void addCards(int minWidth) {
        DirectLarvalMortalityPanel direct = new DirectLarvalMortalityPanel(super.getModelAdapter(), minWidth);
        CompetitiveLarvalMortalityPanel competitive = new CompetitiveLarvalMortalityPanel(super.getModelAdapter());

        super.addStrategyDetailPanel(direct);
        super.addStrategyDetailPanel(competitive);
    }

    private static final Logger log = Logger.getLogger(LarvalMortalityPanel.class);

}
