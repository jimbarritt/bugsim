/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.geometry;

import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.swing.property.NameValuePropertyEditor;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ScaledDistancePropertyEditor extends NameValuePropertyEditor {

    public ScaledDistancePropertyEditor(String propertyName, String labelText, int minWidth) {
        this(propertyName, labelText, minWidth, false);
    }


    public ScaledDistancePropertyEditor(String propertyName, String labelText, int minWidth, boolean includeLogical) {
        super(propertyName, labelText, new ScaledDistanceField(includeLogical), minWidth);
    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return ((ScaledDistanceField)editingComponent).getValue();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        ((ScaledDistanceField)editingComponent).setValue(value);
    }


    public void setUnit(IDistanceUnit defaultUnit) {
        ((ScaledDistanceField)getEditingComponent()).setValue(defaultUnit);
    }
}


