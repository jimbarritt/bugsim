/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 4:47:05 PM by jim
 */
public class StrategyRegistry {

    public StrategyRegistry() {
    }

    public Class getStrategyClassForImplementingClass(String implementingClassName) {
        if (!_strategyClassByImplementingClassMap.containsKey(implementingClassName)) {
            throw new IllegalArgumentException("No strategy class registered for implementingClass: '" + implementingClassName + "'");
        }
        return (Class)_strategyClassByImplementingClassMap.get(implementingClassName);
    }

    public List getStrategyRegistryEntries() {
        return _strategyEntries;
    }

    public void registerStrategy(Class strategyClass, Class implementingClass, String name, String label) {
        StrategyRegistryEntry entry = new StrategyRegistryEntry(strategyClass, implementingClass, name, label);
        _strategyEntries.add(entry);
        _strategyClassByImplementingClassMap.put(implementingClass.getName(), strategyClass);
        _strategyEntryMap.put(strategyClass.getName(), entry);
    }

    public StrategyRegistryEntry getDefaultStrategyEntry() {
        return (StrategyRegistryEntry)_strategyEntries.get(0);
    }

    public StrategyDefinition constructStrategy(StrategyDefinitionParameter strategyS, ParameterMap parameterMap, boolean forwardEvents) {
        String implementingClassName = strategyS.getImplementingClassName();
        Class strategyClass = getStrategyClassForImplementingClass(implementingClassName);
        try {
            Constructor c = strategyClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class, boolean.class});
            return (StrategyDefinition)c.newInstance(new Object[]{strategyS, parameterMap, new Boolean(forwardEvents)});


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StrategyDefinition createDefaultStrategy(String strategyClassName, ParameterMap parameterMap) {
        try {
            Class factoryClass = getFactoryClassForStrategyClass(strategyClassName);
            Method factoryMethod = factoryClass.getMethod(METHOD_DEFAULT_STRATEGY_S_FACTORY, new Class[]{});
            StrategyDefinitionParameter sParam = (StrategyDefinitionParameter)factoryMethod.invoke(null, new Object[]{});
            Constructor c = factoryClass.getConstructor(new Class[]{StrategyDefinitionParameter.class, ParameterMap.class});
            return (StrategyDefinition)c.newInstance(new Object[]{sParam, parameterMap});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class getFactoryClassForStrategyClass(String strategyClassName) {
        return getStrategyClass(strategyClassName);
    }

    private Class getStrategyClass(String strategyClassName) {
        return ((StrategyRegistryEntry)_strategyEntryMap.get(strategyClassName)).getStrategyClass();
    }


    private List _strategyEntries = new ArrayList();
    private Map _strategyClassByImplementingClassMap = new HashMap();
    private Map _strategyEntryMap = new HashMap();


    private static final String METHOD_DEFAULT_STRATEGY_S_FACTORY = "createDefaultStrategyS";
}
