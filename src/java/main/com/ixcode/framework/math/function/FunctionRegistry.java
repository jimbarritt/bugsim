/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.function;

import com.ixcode.framework.simulation.model.landscape.information.function.SensoryInformationFunctionFactory;

import java.util.*;

/**
 *  Description : Used to keep a collection of function instances which you are using so you dont loose the
 * parameteres when you change between them.
 */
public class FunctionRegistry {

    public FunctionRegistry() {
        try {
            List functionNames = SensoryInformationFunctionFactory.instance().getFunctionNames();
            for (Iterator itr = functionNames.iterator(); itr.hasNext();) {
                String name = (String)itr.next();
                IFunction function = SensoryInformationFunctionFactory.instance().createFunction(name);
                addFunction(function);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFunction(IFunction function) {
        _registry.put(function.getName(), function);
        _index.add(function.getName());
    }

    public List getFunctionNames() {
        return _index;
    }

    public IFunction getFunction(String name) {
        return (IFunction)_registry.get(name);
    }


    private List _index = new ArrayList();
    private Map _registry = new HashMap();
}
