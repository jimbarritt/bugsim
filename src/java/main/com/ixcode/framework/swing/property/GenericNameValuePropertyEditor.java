/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;

/**
 *  Description : Just allows you to construct one with any old editing component but wont nescessarily work!
 *  Created     : Feb 8, 2007 @ 2:33:36 PM by jim
 */
public class GenericNameValuePropertyEditor extends NameValuePropertyEditor{

    public GenericNameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth) {
        super(propertyName, labelText, editingComponent, minWidth);
    }

    public GenericNameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth, JComponent extraComponent) {
        super(propertyName, labelText, editingComponent, minWidth, extraComponent);
    }

    public GenericNameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth, boolean stretchEditor) {
        super(propertyName, labelText, editingComponent, minWidth, stretchEditor);
    }

    public GenericNameValuePropertyEditor(String propertyName, String labelText, JComponent editingComponent, int minWidth, boolean stretchEditor, JComponent extraComponent) {
        super(propertyName, labelText, editingComponent, minWidth, stretchEditor, extraComponent);
        

    }

    protected String getValueFromEditingComponent(JComponent editingComponent) {
        return null;
    }

    protected void setValueToEditingComponent(JComponent editingComponent, String value) {

    }
}


