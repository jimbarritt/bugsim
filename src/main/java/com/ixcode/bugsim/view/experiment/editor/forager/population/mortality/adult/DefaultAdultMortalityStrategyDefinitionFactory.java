/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult;

import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.boundary.DefaultBoundaryStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.AdultMortalityStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;

/**
 * @todo consider putting this in the Strategy registry....
 */
public class DefaultAdultMortalityStrategyDefinitionFactory   implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        return AdultMortalityStrategyFactory.createDefaultAdultMortalityStrategy(className, parameterMapLookup.getParameterMap(), true);
    }
    public static final IStrategyDefinitionFactory INSTANCE = new DefaultAdultMortalityStrategyDefinitionFactory();
}
