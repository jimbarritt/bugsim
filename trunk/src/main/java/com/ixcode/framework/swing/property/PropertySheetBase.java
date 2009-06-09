/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class PropertySheetBase extends JPanel implements IPropertySheet , PropertyChangeListener {

    public PropertySheetBase(String name, JavaBeanModelAdapter modelAdapter) {
        super(new BorderLayout());
        _name = name;
        _modelAdapter = modelAdapter;
        _container = new JPanel();
        _container.setLayout(new BoxLayout(_container, BoxLayout.PAGE_AXIS));
        super.add(_container, BorderLayout.NORTH);
    }



    public void add(PropertyGroupPanel panel) {
        _container.add(panel, BorderLayout.NORTH);
        _propertyEditors.addAll(panel.getPropertyValueEditors());
        listenToEditors();
    }

    private void listenToEditors() {
        for (Iterator itr = _propertyEditors.iterator(); itr.hasNext();) {
            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
            if (!editor.isDisplayOnly()) {
//                editor.addPropertyChangeListener(this);
            }
        }
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
//        System.out.println("Property changed!: " + propertyChangeEvent.getPropertyName());
        super.firePropertyChange(propertyChangeEvent.getPropertyName(), propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
    }

    public String getSheetName() {
        return _name;
    }

    public void loadFromBean(Object instance) {


        for (Iterator itr = _propertyEditors.iterator(); itr.hasNext();) {
            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
            if (!editor.isDisplayOnly()) {
                // @todo tidy this up - need some kind of editor which edits / displays the whole bean...
                editor.setValue(_modelAdapter.getPropertyValueAsString(instance, editor.getPropertyName(), Locale.getDefault()));
            }
        }

    }


    public void saveToBean(Object instance) {
        for (Iterator itr = _propertyEditors.iterator(); itr.hasNext();) {
            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
            if (!editor.isDisplayOnly()) {
//                System.out.println("Setting " + editor.getPropertyName() + ", to " + editor.getValue());
                _modelAdapter.setPropertyValueAsString(instance, editor.getPropertyName(), editor.getValue(),  Locale.getDefault());
            }
        }
    }

    public void revertToLastLoaded() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public JComponent getDisplayComponent() {
        return this;
    }

    public void setReadonly(boolean readonly) {
        for (Iterator itr = _propertyEditors.iterator(); itr.hasNext();) {
            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
            editor.setReadonly(readonly);
        }
    }


    private String _name;
    private JPanel _container;
    private List _propertyEditors = new ArrayList();
    private JavaBeanModelAdapter _modelAdapter;
}
