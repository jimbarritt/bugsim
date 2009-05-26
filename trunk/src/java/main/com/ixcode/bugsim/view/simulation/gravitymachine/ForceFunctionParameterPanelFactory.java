/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.GaussianDistributionFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.GaussianDistributionGradientFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.LinearDecaySignalFunction;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ForceFunctionParameterPanelFactory {


    public static ForceFunctionParameterPanelFactory instance() {
        return INSTANCE;
    }

    private ForceFunctionParameterPanelFactory() {
        registerParameterPanel(ExponentialDecaySignalFunction.class, ExponentialParametersPanel.class);
        registerParameterPanel(LinearDecaySignalFunction.class, LinearParametersPanel.class);
        registerParameterPanel(GaussianDistributionFunction.class, NormalParametersPanel.class);
        registerParameterPanel(GaussianDistributionGradientFunction.class, NormalParametersPanel.class);
    }

    public void registerParameterPanel(Class forceFunction, Class parameterPanel) {
        _registry.put(forceFunction.getName(), parameterPanel);
    }

    public JComponent createParameterPanel(ISignalFunction function) {
        try {
            Class parameterPanelClass = (Class)_registry.get(function.getClass().getName());
            Constructor c = parameterPanelClass.getConstructor(new Class[]{ISignalFunction.class});
            return (JComponent)c.newInstance(new Object[]{function});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

    }

    private static ForceFunctionParameterPanelFactory INSTANCE = new ForceFunctionParameterPanelFactory();
    private Map _registry = new HashMap();

}
