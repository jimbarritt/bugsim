/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.larval;

import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.DefaultImmigrationStrategyDefinitionFactory;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.ImmigrationStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.LarvalMortalityStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 * @todo consider putting this in the Strategy registry....
 */
public class DefaultLarvalMortalityStrategyDefinitionFactory   implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        return LarvalMortalityStrategyFactory.createDefaultLarvalMortalityStrategy(className, parameterMapLookup.getParameterMap(), true);
    }
    public static final IStrategyDefinitionFactory INSTANCE = new DefaultLarvalMortalityStrategyDefinitionFactory();
}
