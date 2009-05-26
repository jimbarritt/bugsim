/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.math.function.FunctionRegistry;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ForceFunctionComboBox extends JComboBox {

    public ForceFunctionComboBox(FunctionRegistry registry) {
        _registry = registry;
        List functionNames = _registry.getFunctionNames();
        for (Iterator itr = functionNames.iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            super.addItem(name);
        }

    }

    public ISignalFunction getSelectedFunction() {
        String name = (String)getSelectedItem();
        try {
            return  (ISignalFunction)_registry.getFunction(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FunctionRegistry _registry;
}
