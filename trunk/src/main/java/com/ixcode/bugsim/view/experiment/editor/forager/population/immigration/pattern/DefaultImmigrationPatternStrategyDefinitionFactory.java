/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.pattern;

import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.forager.population.immigration.DefaultImmigrationStrategyDefinitionFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ImmigrationStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern.ImmigrationPatternStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 * @todo consider putting this in the Strategy registry....
 */
public class DefaultImmigrationPatternStrategyDefinitionFactory   implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        return ImmigrationPatternStrategyFactory.createDefaultImmigrationPatternStrategy(className, parameterMapLookup.getParameterMap(), true);
    }
    public static final IStrategyDefinitionFactory INSTANCE = new DefaultImmigrationPatternStrategyDefinitionFactory();
}
