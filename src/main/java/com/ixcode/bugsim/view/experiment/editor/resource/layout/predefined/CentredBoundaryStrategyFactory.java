/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 31, 2007 @ 6:49:56 PM by jim
 */
public class CentredBoundaryStrategyFactory implements IStrategyDefinitionFactory {


    public CentredBoundaryStrategyFactory(ICentredBoundaryContainer container) {
        _container = container;
    }

    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        BoundaryStrategyBase strategy = BoundaryStrategyFactory.createDefaultBoundaryStrategy(className, parameterMapLookup.getParameterMap(), true);
        ResourceLayoutStrategyBase.centreOnLandscape(_container.getResourceLayoutStrategy(), strategy, LandscapeCategory.findInParameterMap(parameterMapLookup.getParameterMap()));
        return strategy;
    }


    private ResourceLayoutStrategyBase _layoutStrategy;
    private ICentredBoundaryContainer _container;
}
