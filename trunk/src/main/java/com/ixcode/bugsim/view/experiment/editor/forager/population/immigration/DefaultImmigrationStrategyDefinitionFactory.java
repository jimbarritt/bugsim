/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration;

import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.forager.population.mortality.adult.DefaultAdultMortalityStrategyDefinitionFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.AdultMortalityStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ImmigrationStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 * @todo consider putting this in the Strategy registry....
 */
public class DefaultImmigrationStrategyDefinitionFactory   implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        return ImmigrationStrategyFactory.createDefaultImmigrationStrategy(className, parameterMapLookup.getParameterMap(), true);
    }
    public static final IStrategyDefinitionFactory INSTANCE = new DefaultImmigrationStrategyDefinitionFactory();
}
