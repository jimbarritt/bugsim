/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.signal;

import com.ixcode.bugsim.agent.cabbage.signal.MultipleSurfaceSignalFactory;
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
public class SignalStrategyFactory {

    private SignalStrategyFactory() {
    }

    public static SignalStrategyBase createSignalStrategy(StrategyDefinitionParameter signalS, ParameterMap params, boolean isForwardEvents) {
        String className = signalS.getImplementingClassName();

        Class strategyClass = getStrategyClass(className);
        try {
            Constructor c = strategyClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class, boolean.class});
            return (SignalStrategyBase)c.newInstance(new Object[]{signalS, params, new Boolean(isForwardEvents)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private static Class getStrategyClass(String strategyClassName) {
        if (!CLASS_MAP.containsKey(strategyClassName)) {
            throw new IllegalArgumentException("No layout class registered for strategyClassName: '" + strategyClassName + "'");
        }
        return (Class)SignalStrategyFactory.CLASS_MAP.get(strategyClassName);
    }

    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    private static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(MultipleSurfaceSignalStrategy.class, MultipleSurfaceSignalFactory.class,  MultipleSurfaceSignalStrategy.S_MULTIPLE_SURFACE, "Multiple Surface");
    }

    private static final Map CLASS_MAP = new HashMap();
    static {
        SignalStrategyFactory.CLASS_MAP.put(MultipleSurfaceSignalFactory.class.getName(), MultipleSurfaceSignalStrategy.class);
    }
}
