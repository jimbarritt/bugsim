/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.swing.IxApplyPropertyChangeAction;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SamplingParametersPanel extends ModelBasedPropertyGroupPanel {


    public static final String TITLE = "Sampling Parameters";

    public SamplingParametersPanel(ChartSamplingParameters model) {
        super(TITLE);

        super.addProperty(ChartSamplingParameters.PROPERTY_RANGE_START, "Start", model);
        super.addProperty(ChartSamplingParameters.PROPERTY_RANGE_END, "End", model);
        super.addProperty(ChartSamplingParameters.PROPERTY_FREQUENCY, "Frequency", model);
        super.addButton(new IxApplyPropertyChangeAction(this, model));

    }


}
