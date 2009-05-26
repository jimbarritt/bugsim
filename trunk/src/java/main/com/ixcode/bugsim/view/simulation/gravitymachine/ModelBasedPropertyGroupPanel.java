/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;


import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.model.ModelProperty;
import com.ixcode.framework.swing.property.PropertyGroupPanel;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ModelBasedPropertyGroupPanel extends PropertyGroupPanel {

    public ModelBasedPropertyGroupPanel(String title) {
        super(title);
    }

    public void addProperty(String name, String labelText, ModelBase model) {
        ModelProperty property = model.getProperty(name);
                Class propertyType = property.getType();
        String initialValue = model.getPropertyValueAsString(name);
        super.addPropertyEditor(name, labelText, propertyType, initialValue, 30);
    }
}
