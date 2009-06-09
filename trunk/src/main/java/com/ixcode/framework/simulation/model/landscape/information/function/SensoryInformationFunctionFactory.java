/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information.function;

import com.ixcode.framework.math.function.IFunction;

import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SensoryInformationFunctionFactory {


    private SensoryInformationFunctionFactory() {
        registerFunction(ExponentialDecaySignalFunction.NAME, ExponentialDecaySignalFunction.class);
        registerFunction(LinearDecaySignalFunction.NAME, LinearDecaySignalFunction.class);
        registerFunction(GaussianDistributionFunction.NAME, GaussianDistributionFunction.class);
        registerFunction(GaussianDistributionGradientFunction.NAME, GaussianDistributionGradientFunction.class);
    }




    public void registerFunction(String name, Class function) {
        _registry.put(name, function);
        _index.add(name);
    }

    public static SensoryInformationFunctionFactory instance() {
        return INSTANCE;
    }

    public IFunction createFunction(String name) throws IllegalAccessException, InstantiationException {

            Class function = (Class)_registry.get(name);
            return (IFunction)function.newInstance();
    }

    public List createAllFunctions() throws IllegalAccessException, InstantiationException {
        List functions = new ArrayList();
        for (Iterator itr = _index.iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            functions.add(createFunction(name));
        }
        return functions;
    }

    public List getFunctionNames() {
        return _index;
    }

    private static SensoryInformationFunctionFactory INSTANCE = new SensoryInformationFunctionFactory();

    private Map _registry = new HashMap();
    private List _index = new ArrayList();
}
