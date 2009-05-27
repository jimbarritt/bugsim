/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public interface IPropertySheet {

    String getSheetName();

    void loadFromBean(Object instance);

    void saveToBean(Object instance);

    void revertToLastLoaded();

    JComponent getDisplayComponent();

    void addPropertyChangeListener(PropertyChangeListener listener);


    void setReadonly(boolean readonly);
}
