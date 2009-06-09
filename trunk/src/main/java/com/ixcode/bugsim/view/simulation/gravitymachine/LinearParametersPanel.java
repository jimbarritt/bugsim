/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.LinearDecaySignalFunction;
import com.ixcode.framework.swing.IxApplyPropertyChangeAction;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LinearParametersPanel extends ModelBasedPropertyGroupPanel {

    public static final String TITLE = "Linear Decay Parameters";
    public LinearParametersPanel(ISignalFunction model) {
        super(TITLE);
        _function = (LinearDecaySignalFunction)model;
        super.addProperty(LinearDecaySignalFunction.PROPERTY_MAX, "Maximum", _function);
        super.addProperty(LinearDecaySignalFunction.PROPERTY_INTERCEPTOR, "Intercept", _function);
        super.addProperty(LinearDecaySignalFunction.PROPERTY_MULTIPLIER, "Multiplier", _function);

        super.addButton(new IxApplyPropertyChangeAction(this, _function));
    }

    private LinearDecaySignalFunction _function;
}
