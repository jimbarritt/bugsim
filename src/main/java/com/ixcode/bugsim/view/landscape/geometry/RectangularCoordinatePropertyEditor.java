/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.geometry;

import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinateFormat;
import com.ixcode.framework.swing.property.NameValuePropertyEditor;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class RectangularCoordinatePropertyEditor extends NameValuePropertyEditor {

    public RectangularCoordinatePropertyEditor(String propertyName, String labelText, int minWidth, int decimalPlaces) {
        super(propertyName, labelText, RectangularCoordinatePropertyEditor.createEditingComponent(decimalPlaces), minWidth);
        getRCoordField().addDocumentListener(this);
    }

    private static JComponent createEditingComponent(int decimalPlaces) {
        return new RectangularCoordinateField(decimalPlaces);
    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return _coordFormat.format(getRCoordField().getEditedValue());
    }

    private RectangularCoordinateField getRCoordField() {
        return (RectangularCoordinateField)super.getEditingComponent();
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {
        try {
            RectangularCoordinate b = (RectangularCoordinate)_coordFormat.parse(value);
            getRCoordField().setEditedValue(b);
        } catch (JavaBeanParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void reformatValue(String modelValue) {
        super.reformatValue(modelValue);
        getRCoordField().reformatValue();
    }

    RectangularCoordinateFormat _coordFormat = new RectangularCoordinateFormat();

}
