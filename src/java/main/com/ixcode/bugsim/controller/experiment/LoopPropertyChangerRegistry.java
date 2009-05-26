/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated 
 */
public class LoopPropertyChangerRegistry {

    private LoopPropertyChangerRegistry() {
        registerPropertyChanger(Integer.class, IntegerPropertyValueIterator.class);
    }

    public void registerPropertyChanger(Class valueClass, Class changer) {
        _changers.put(valueClass.getName(), changer);
    }

    public static LoopPropertyChangerRegistry getInstance() {
        return INSTANCE;
    }

    public Class getChangerClass(Class valueClass) {
        return (Class)_changers.get(valueClass.getName());
    }

    private static final LoopPropertyChangerRegistry INSTANCE = new LoopPropertyChangerRegistry();
    private Map _changers = new HashMap();
}
