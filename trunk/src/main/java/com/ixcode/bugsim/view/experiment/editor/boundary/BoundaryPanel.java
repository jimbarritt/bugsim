/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import org.apache.log4j.Logger;

/**
 * Description : Manages the boundary Strategy
 */
public class BoundaryPanel extends StrategyPanel {

    public BoundaryPanel(String name, IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup) {
        super(name, modelAdapter, parameterMapLookup, BoundaryStrategyFactory.getRegistry(), "Shape", 100);

        initStrategyDetailPanels(modelAdapter);
    }

    private void initStrategyDetailPanels(IModelAdapter modelAdapter) {
        _rectangularBoundaryStrategyPanel = new RectangularBoundaryStrategyPanel(modelAdapter);
        _circularBoundaryStrategyPanel = new CircularBoundaryStrategyPanel(modelAdapter);

        super.addStrategyDetailPanel(_rectangularBoundaryStrategyPanel);
        super.addStrategyDetailPanel(_circularBoundaryStrategyPanel);


    }


    public void replaceStrategyDefinition(StrategyDefinitionParameter strategyS) {
        if (getStrategyDefinition().getStrategyS() != strategyS) {
//            if (log.isInfoEnabled()) {
//                log.info("Refreshing Strategy [" + getStrategyDefinition() + "] with: " + strategyS);
//
//            }
            final String implementingClassName = strategyS.getImplementingClassName();
            final String className = BoundaryStrategyFactory.getRegistry().getStrategyClassForImplementingClass(implementingClassName).getName();
//            super.setStrategyDefinition(className, strategyS);
            setStrategyClassName(className);
        }
    }

    public void updateDerivedStatus() {
        _rectangularBoundaryStrategyPanel.updateDerivedStatus();
        _circularBoundaryStrategyPanel.updateDerivedStatus();
    }


    private static final Logger log = Logger.getLogger(BoundaryPanel.class);


    private RectangularBoundaryStrategyPanel _rectangularBoundaryStrategyPanel;
    private CircularBoundaryStrategyPanel _circularBoundaryStrategyPanel;
}
