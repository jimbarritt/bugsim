/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.movelength;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.movelength.MoveLengthStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:29:43 PM by jim
 */
public class MoveLengthGeneratorPanel extends StrategyPanel {

    public MoveLengthGeneratorPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("MoveLengthGenerator", modelAdapter, parameterMapLookup, MoveLengthStrategyFactory.getRegistry(), "Type", minWidth);
        initCards();
        super.setChangeTypeEnabled(false);
    }

    private void initCards() {
        FixedMoveLengthStrategyPanel fixedMoveLengthPanel = new FixedMoveLengthStrategyPanel(super.getModelAdapter());
        super.addStrategyDetailPanel(fixedMoveLengthPanel);
    }



}
