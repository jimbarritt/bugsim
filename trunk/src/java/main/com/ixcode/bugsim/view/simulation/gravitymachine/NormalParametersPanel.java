/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.function.GaussianDistributionFunction;
import com.ixcode.framework.swing.IxApplyPropertyChangeAction;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class NormalParametersPanel extends ModelBasedPropertyGroupPanel {

    public static final String TITLE = "Normal Decay Parameters";
    public NormalParametersPanel(ISignalFunction model) {
        super(NormalParametersPanel.TITLE);
        _function = (GaussianDistributionFunction)model;

        super.addProperty(GaussianDistributionFunction.PROP_MEAN, "Mean", _function);
        super.addProperty(GaussianDistributionFunction.PROP_STD_DEV, "s.d.", _function);
        super.addProperty(GaussianDistributionFunction.PROP_MAGNIFICATION, "magnification", _function);

        super.addButton(new IxApplyPropertyChangeAction(this, _function));
    }



    private GaussianDistributionFunction _function;
}
