/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.layout;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutStrategy;
import com.ixcode.bugsim.agent.cabbage.layout.PredefinedResourceLayout;
import com.ixcode.bugsim.agent.cabbage.layout.CalculatedResourceLayout;

import java.lang.reflect.Constructor;


/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 11:35:03 AM by jim
 */
public class ResourceLayoutStrategyFactory {

    private ResourceLayoutStrategyFactory() {
    }

    public static ResourceLayoutStrategyBase createResourceLayoutStrategy(StrategyDefinitionParameter layoutS, ParameterMap params, boolean forwardEvents) {
        String className = layoutS.getImplementingClassName();

        Class strategyClass = getLayoutStrategyClass(className);
        try {
            Constructor c = strategyClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class, boolean.class});
            return (ResourceLayoutStrategyBase)c.newInstance(new Object[]{layoutS, params, new Boolean(forwardEvents)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private static Class getLayoutStrategyClass(String implementingClassName) {
        return REGISTRY.getStrategyClassForImplementingClass(implementingClassName);
    }

    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    public static StrategyDefinition createDefaultLayoutS(String className, ParameterMap parameterMap) {
        return REGISTRY.createDefaultStrategy(className, parameterMap);
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(PredefinedResourceLayoutStrategy.class,  PredefinedResourceLayout.class,  PredefinedResourceLayoutStrategy.STRATEGY_NAME, "Predefined");
        REGISTRY.registerStrategy(CalculatedResourceLayoutStrategy.class,  CalculatedResourceLayout.class,  CalculatedResourceLayoutStrategy.STRATEGY_NAME, "Calculated");
//        LevyWalkResourceLayoutStrategy.register(REGISTRY);
    }



}
