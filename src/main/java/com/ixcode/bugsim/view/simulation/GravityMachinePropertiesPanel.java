/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation;

import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculator;
import com.ixcode.framework.swing.property.PropertyPanel;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GravityMachinePropertiesPanel extends PropertyPanel {

    public static final String TITLE = "Gravity Machine Properties";

    public GravityMachinePropertiesPanel(GravitationalCalculator gravitationalCalculator) {
        super(TITLE);

        super.addPropertyEditor("F Cuttoff", GravitationalCalculator.PROPERTY_DISTANCE_CUTOFF, "" + gravitationalCalculator.getDistanceCutoff());

    }

    public double getDistanceCuttoffValue() {
        return super.getDoubleTextFieldValue(GravitationalCalculator.PROPERTY_DISTANCE_CUTOFF);
    }


}
