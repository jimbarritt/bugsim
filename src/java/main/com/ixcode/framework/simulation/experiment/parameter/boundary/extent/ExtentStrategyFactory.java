/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExtentStrategyFactory {

    public static ExtentStrategyBase createExtentStrategy(String strategyClassName, ParameterMap params) {
        try {
            String factoryClassName = getFactoryClassForStrategyClass(strategyClassName);
            Class factoryClass = Thread.currentThread().getContextClassLoader().loadClass(factoryClassName);
            Method factoryMethod = factoryClass.getMethod(METHOD_DEFAULT_PARAMETER_FACTORY, new Class[]{});
            StrategyDefinitionParameter sParam = (StrategyDefinitionParameter)factoryMethod.invoke(null, new Object[] {});
            Constructor c = factoryClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class});
            return (ExtentStrategyBase)c.newInstance(new Object[] {sParam, params});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFactoryClassForStrategyClass(String strategyClassName) {
        if (!FACTORY_MAP.containsKey(strategyClassName)) {
            throw new IllegalArgumentException("No Factory Class registered for Strategy: '" + strategyClassName + "'");
        }

        return (String)FACTORY_MAP.get(strategyClassName);
    }

    public static StrategyDefinitionParameter createBoundaryS(String name, Class boundaryStrategyClass, StrategyDefinitionParameter outerBoundaryS) {
        StrategyDefinitionParameter boundaryS  = new StrategyDefinitionParameter(name, boundaryStrategyClass.getName());
        boundaryS.addParameter(new Parameter(ExtentStrategyBase.P_OUTER_BOUNDARY, outerBoundaryS));

        return boundaryS;
    }

    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }

    private static StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(FixedExtentStrategy.class, FixedExtentStrategy.class, FixedExtentStrategy.S_FIXED_EXTENT, "Fixed");
        REGISTRY.registerStrategy(DistancedExtentStrategy.class, DistancedExtentStrategy.class, DistancedExtentStrategy.S_DISTANCED_EXTENT, "Distanced");
//        REGISTRY.registerStrategy(ProportionalExtentStrategy.class, ProportionalExtentStrategy.class, ProportionalExtentStrategy.S_PROPORTIONAL_EXTENT, "Proportional");
    }

    private static final Map FACTORY_MAP = new HashMap();
    static {
        FACTORY_MAP.put(DistancedExtentStrategy.class.getName(), DistancedExtentStrategyFactory.class.getName());
        FACTORY_MAP.put(FixedExtentStrategy.class.getName(), FixedExtentStrategy.class.getName());
        FACTORY_MAP.put(ProportionalExtentStrategy.class.getName(), ProportionalExtentStrategy.class.getName());
    }

    private static final String METHOD_DEFAULT_PARAMETER_FACTORY = "createDefaultStrategyParameter";
}
