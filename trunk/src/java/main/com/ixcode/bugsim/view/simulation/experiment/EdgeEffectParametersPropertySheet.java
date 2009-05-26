/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.experiment;

import com.ixcode.bugsim.controller.experiment.RandomWalkEdgeEffectProperties;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.swing.property.PropertyGroupPanel;
import com.ixcode.framework.swing.property.PropertySheetBase;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EdgeEffectParametersPropertySheet extends PropertySheetBase {

    public EdgeEffectParametersPropertySheet(JavaBeanModelAdapter modelAdapter) {
        super("Parameters", modelAdapter);

        PropertyGroupPanel populationPanel = new PropertyGroupPanel("Population");
        populationPanel.addPropertyEditor(RandomWalkEdgeEffectProperties.PARAM_NUMBER_OF_BUTTERFLIES, "Number Of Butterflies", 10, TextAlignment.RIGHT, 40);
        populationPanel.addPropertyEditor(RandomWalkEdgeEffectProperties.PARAM_POPULATION_SIZE, "Population Size", 10, TextAlignment.RIGHT, 40);
        super.add(populationPanel);

        PropertyGroupPanel butterflyPanel = new PropertyGroupPanel("Butterflies");
        butterflyPanel.addPropertyEditor(RandomWalkEdgeEffectProperties.PARAM_BUTTERFLY_MOVE_LENGTH, "Move Length", 10, TextAlignment.RIGHT, 40);
        butterflyPanel.addPropertyEditor(RandomWalkEdgeEffectProperties.PARAM_BUTTERFLY_ANGLE_OF_TURN_SD, "Angle Of Turn SD", 10, TextAlignment.RIGHT, 40);
        butterflyPanel.addPropertyEditor(RandomWalkEdgeEffectProperties.PARAM_BUTTERFLY_NUMBER_OF_EGGS, "Number Of Eggs", 10, TextAlignment.RIGHT, 40);
        super.add(butterflyPanel);

     
    }




}
