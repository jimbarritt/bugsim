/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;
import com.ixcode.framework.swing.IxApplyPropertyChangeAction;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExponentialParametersPanel extends ModelBasedPropertyGroupPanel {

    public static final String TITLE = "Exponential Decay Parameters";
    public ExponentialParametersPanel(ISignalFunction model) {
        super(TITLE);
        _function = (ExponentialDecaySignalFunction)model;
        super.addProperty(ExponentialDecaySignalFunction.PROPERTY_GRAVITATIONAL_CONSTANT, "Exponent", _function);
        super.addProperty(ExponentialDecaySignalFunction.PROPERTY_MAX_FORCE, "Max Force", _function);
        super.addProperty(ExponentialDecaySignalFunction.PROPERTY_SCALE, "Scaling Factor", _function);
        super.addButton(new IxApplyPropertyChangeAction(this, _function));
    }

    private ExponentialDecaySignalFunction _function;
}
