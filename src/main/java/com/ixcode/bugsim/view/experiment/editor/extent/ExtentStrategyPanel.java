/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.extent;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.ExtentStrategyFactory;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExtentStrategyPanel extends StrategyPanel {

    public ExtentStrategyPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterLookup, int minWidth) {
        super("ExtentStrategyPanel", modelAdapter, parameterLookup, ExtentStrategyFactory.getRegistry(), "Type", minWidth);

        initStrategyCards(modelAdapter);
    }

    private void initStrategyCards(IModelAdapter modelAdapter) {
        FixedExtentStrategyPanel fixedExtentPanel = new FixedExtentStrategyPanel(modelAdapter);
        DistancedExtentStrategyPanel distancedExtentStrategyPanel = new DistancedExtentStrategyPanel(modelAdapter);
        ProportionalExtentStrategyPanel _proportionalExtentStrategyPanel = new ProportionalExtentStrategyPanel(modelAdapter);

        super.addStrategyDetailPanel(fixedExtentPanel);
        super.addStrategyDetailPanel(distancedExtentStrategyPanel);
//        super.addStrategyDetailPanel(_proportionalExtentStrategyPanel);
    }






}
