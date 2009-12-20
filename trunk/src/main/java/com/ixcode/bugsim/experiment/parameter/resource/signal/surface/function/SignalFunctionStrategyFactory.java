/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.function;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.model.landscape.information.function.GaussianDistributionFunction;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 11:35:03 AM by jim
 *
 * @todo implement a base class for these ?
 */
public class SignalFunctionStrategyFactory {

    private SignalFunctionStrategyFactory() {
    }

    public static SignalFunctionStrategyBase createSignalSurface(StrategyDefinitionParameter surfaceS, ParameterMap params) {
        String className = surfaceS.getImplementingClassName();

        Class strategyClass = SignalFunctionStrategyFactory.getStrategyClass(className);
        try {
            Constructor c = strategyClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class});
            return (SignalFunctionStrategyBase)c.newInstance(new Object[]{surfaceS, params});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private static Class getStrategyClass(String strategyClassName) {
        if (!SignalFunctionStrategyFactory.CLASS_MAP.containsKey(strategyClassName)) {
            throw new IllegalArgumentException("No class registered for strategyClassName: '" + strategyClassName + "'");
        }
        return (Class)SignalFunctionStrategyFactory.CLASS_MAP.get(strategyClassName);
    }


    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }
    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(GaussianSignalFunctionStrategy.class, GaussianDistributionFunction.class, GaussianSignalFunctionStrategy.S_GAUSSIAN_SIGNAL_FUNCTION, "Gaussian Distribution");
    }
    private static final Map CLASS_MAP = new HashMap();
    static {
        CLASS_MAP.put(GaussianDistributionFunction.class.getName(), GaussianSignalFunctionStrategy.class);
    }
}
