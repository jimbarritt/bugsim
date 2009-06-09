/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.strategy;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.simulation.model.landscape.boundary.CircularBoundary;
import com.ixcode.framework.simulation.model.landscape.boundary.LinearBoundary;
import com.ixcode.framework.simulation.model.landscape.boundary.RectangularBoundary;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryStrategyFactory {

    
    public static BoundaryStrategyBase  createBoundaryStrategy(StrategyDefinitionParameter sParam, ParameterMap params, boolean forwardParameterChanges) {
        String strategyClassName = sParam.getImplementingClassName();
        Class boundaryClass = getBoundaryStrategyClass(strategyClassName);

        try {

            Constructor c = boundaryClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class, boolean.class});
            return  (BoundaryStrategyBase)c.newInstance(new Object[]{sParam, params, new Boolean(forwardParameterChanges)});


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class getBoundaryStrategyClass(String strategyClassName) {
        if (!CLASS_MAP.containsKey(strategyClassName)) {
            throw new IllegalArgumentException("No boundary class registered for strategyClassName: '" + strategyClassName + "'");
        }
        return (Class)CLASS_MAP.get(strategyClassName);
    }

    /**
     * @todo move this into the StrategyRegistry ?
     * @param className
     * @param params
     * @return
     */
    public static BoundaryStrategyBase createDefaultBoundaryStrategy(String className, ParameterMap params, boolean forwardEvents) {
        try {
            Class strategyClass = Thread.currentThread().getContextClassLoader().loadClass(className);
            Method factoryMethod = strategyClass.getMethod(METHOD_DEFAULT_PARAMETER_FACTORY, new Class[]{});
            StrategyDefinitionParameter sParam = (StrategyDefinitionParameter)factoryMethod.invoke(null, new Object[] {});
            Constructor c = strategyClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class, boolean.class});
            return (BoundaryStrategyBase)c.newInstance(new Object[] {sParam, params, new Boolean(forwardEvents)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static StrategyRegistry getRegistry() {
        return REGISTRY;
    }
    private static final String METHOD_DEFAULT_PARAMETER_FACTORY = "createDefaultStrategyParameter";


    public static final StrategyRegistry REGISTRY = new StrategyRegistry();
    static {
        REGISTRY.registerStrategy(RectangularBoundaryStrategy.class, RectangularBoundary.class, RectangularBoundaryStrategy.S_RECTANGULAR, "Rectangular");
        REGISTRY.registerStrategy(CircularBoundaryStrategy.class, CircularBoundary.class, CircularBoundaryStrategy.S_CIRCULAR, "Circular");
    }

    private static final Map CLASS_MAP = new HashMap();
    static {
        CLASS_MAP.put(RectangularBoundary.class.getName(), RectangularBoundaryStrategy.class);
        CLASS_MAP.put(CircularBoundary.class.getName(), CircularBoundaryStrategy.class);
        CLASS_MAP.put(LinearBoundary.class.getName(), LinearBoundaryStrategy.class);
    }
    private static final Logger log = Logger.getLogger(BoundaryStrategyFactory.class);
}
