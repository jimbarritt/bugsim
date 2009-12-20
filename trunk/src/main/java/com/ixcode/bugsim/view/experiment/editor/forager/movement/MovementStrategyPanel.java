/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.MovementStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 4, 2007 @ 8:56:27 PM by jim
 */
public class MovementStrategyPanel extends StrategyPanel {
    public MovementStrategyPanel(IModelAdapter modelAdapter, IParameterMapLookup paramMapLookup) {
        super("MovementStrategy", modelAdapter, paramMapLookup, MovementStrategyFactory.getRegistry(), "Type", 100);
        initCards();
    }

    private void initCards() {
        _randomWalkPanel = new RandomWalkStrategyPanel(super.getModelAdapter());
        super.addStrategyDetailPanel(_randomWalkPanel);
        SensoryRandomWalkStrategyPanel sensoryPanel = new SensoryRandomWalkStrategyPanel(super.getModelAdapter());
        super.addStrategyDetailPanel(sensoryPanel);
    }

    private RandomWalkStrategyPanel _randomWalkPanel;
}
