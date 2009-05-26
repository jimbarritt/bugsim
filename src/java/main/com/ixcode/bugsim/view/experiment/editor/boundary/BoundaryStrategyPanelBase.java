/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
abstract class BoundaryStrategyPanelBase extends StrategyDetailsPanel {


    protected BoundaryStrategyPanelBase(IModelAdapter modelAdapter, Class strategyClass) {
        super(modelAdapter, strategyClass);
    }






    private static final Logger log = Logger.getLogger(BoundaryStrategyPanelBase.class);

    private BoundaryStrategyBase _boundaryStrategy;
    
}
