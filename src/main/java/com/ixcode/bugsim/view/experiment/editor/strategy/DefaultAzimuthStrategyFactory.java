/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.strategy;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 4:20:23 PM by jim
 */
public class DefaultAzimuthStrategyFactory implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
        return AzimuthStrategyFactory.createDefaultAzimuthStrategy(className, parameterMapLookup.getParameterMap());
    }

    public static final IStrategyDefinitionFactory INSTANCE = new DefaultAzimuthStrategyFactory();
}
