/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.movement.azimuth;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:45:53 PM by jim
 */
public class AzimuthGeneratorPanel extends StrategyPanel {

    public AzimuthGeneratorPanel(IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, int minWidth) {
        super("AzimuthGeneratorPanel", modelAdapter, parameterMapLookup, AzimuthStrategyFactory.getRegistry(), "Type", minWidth);
        initCards(160);
    }

    private void initCards(int minWidth) {

        GaussianAzimuthStrategyPanel gaussianPanel = new GaussianAzimuthStrategyPanel(super.getModelAdapter(), minWidth);
        VonMisesAzimuthStrategyPanel vonmisesPanel = new VonMisesAzimuthStrategyPanel(super.getModelAdapter(), minWidth);
        WrappedNormalAzimuthStrategyPanel wrappedNormalPanel = new WrappedNormalAzimuthStrategyPanel(super.getModelAdapter(), minWidth);
        WrappedCauchyAzimuthStrategyPanel wrappedCauchyPanel = new WrappedCauchyAzimuthStrategyPanel(super.getModelAdapter(), minWidth);

        super.addStrategyDetailPanel(gaussianPanel);
        super.addStrategyDetailPanel(vonmisesPanel);
        super.addStrategyDetailPanel(wrappedNormalPanel);
        super.addStrategyDetailPanel(wrappedCauchyPanel);

    }


}
