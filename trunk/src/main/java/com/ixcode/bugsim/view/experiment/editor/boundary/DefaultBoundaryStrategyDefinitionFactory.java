/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;

/**
 * @todo consider putting this in the Strategy registry....
 */
public class DefaultBoundaryStrategyDefinitionFactory   implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        return BoundaryStrategyFactory.createDefaultBoundaryStrategy(className, parameterMapLookup.getParameterMap(), true);
    }
    public static final IStrategyDefinitionFactory INSTANCE = new DefaultBoundaryStrategyDefinitionFactory();
}
