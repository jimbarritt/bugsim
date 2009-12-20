/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.signal.surface;

import com.ixcode.bugsim.agent.cabbage.signal.FunctionalSignalSurfaceFactory;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 11:35:03 AM by jim
 *
 * @todo implement a base class for these ?
 */
public class SignalSurfaceStrategyFactory {

    private SignalSurfaceStrategyFactory() {
    }

    public static SignalSurfaceStrategyBase createSignalSurface(StrategyDefinitionParameter surfaceS, ParameterMap params) {
        String className = surfaceS.getImplementingClassName();

        Class strategyClass = SignalSurfaceStrategyFactory.getStrategyClass(className);
        try {
            Constructor c = strategyClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class});
            return (SignalSurfaceStrategyBase)c.newInstance(new Object[]{surfaceS, params});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private static Class getStrategyClass(String strategyClassName) {
        if (!SignalSurfaceStrategyFactory.CLASS_MAP.containsKey(strategyClassName)) {
            throw new IllegalArgumentException("No class registered for strategyClassName: '" + strategyClassName + "'");
        }
        return (Class)SignalSurfaceStrategyFactory.CLASS_MAP.get(strategyClassName);
    }

    public static StrategyRegistry getRegistry()  {
        return REGISTRY;
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(FunctionalSignalSurfaceStrategy.class,  FunctionalSignalSurfaceFactory.class,  FunctionalSignalSurfaceStrategy.S_FUNCTIONAL_SIGNAL_SURFACE, "Functional Surface");
    }

    private static final Map CLASS_MAP = new HashMap();
    static {
        CLASS_MAP.put(FunctionalSignalSurfaceFactory.class.getName(), FunctionalSignalSurfaceStrategy.class);
    }
}
