/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;
import java.awt.event.FocusListener;

public interface IPropertyValueEditor {

    String getPropertyName();

    JComponent getDisplayComponent();

    String getValue();

    void setValue(String value);

    /**
     * We use this rather than the standard PropertyChangeListener because it can get confusing
     * if you happen to have a property calle "bounds" say - it gets mixed up with all th eswing properties.
     * @param l
     */
    void addPropertyValueEditorListener(IPropertyValueEditorListener l);
    void addFocusListener(FocusListener focusListener);

    
    void setReadonly(boolean readonly);
    boolean isReadonly();





    void reformatValue(String modelValue);

    JComponent getEditingComponent();

    static final String EDITED_PROPERTY = "editedProperty";

    boolean isDisplayOnly();
}
