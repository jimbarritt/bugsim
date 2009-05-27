/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.model.IModelAdapter;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class PropertyBinding implements PropertyChangeListener, IPropertyValueEditorListener, FocusListener {
    public static void bindEditor(IPropertyValueEditor editor, IModelAdapter modelAdapter, Object model) throws JavaBeanException {
        PropertyBinding binding = new PropertyBinding(editor, modelAdapter);
        binding.bind(model);
    }


    public PropertyBinding(IPropertyValueEditor editor, IModelAdapter modelAdapter) {

        _editor = editor;
        _propertyName = _editor.getPropertyName();
        _modelAdapter = modelAdapter;

        if (!_editor.isDisplayOnly()) {
            _editor.addPropertyValueEditorListener(this);
            _editor.addFocusListener(this);
        }

    }

    public void bind(Object bean) throws JavaBeanException {
        _bean = bean;
        if (bean != null) {
            setValueToEditor();
            addModelListener(bean, _propertyName);
        }
    }

    public void release(Object bean) throws JavaBeanException {
        if (bean != null) {
            removeModelListener(bean, _propertyName);
        }
    }

    private void addModelListener(Object bean, String propertyName) throws JavaBeanException {
        try {
            Method addPropertyListener = bean.getClass().getMethod("addPropertyChangeListener", new Class[]{String.class, PropertyChangeListener.class});

            addPropertyListener.invoke(bean, new Object[]{propertyName, this});

        } catch (NoSuchMethodException e) {
            throw new JavaBeanException("Could not find addPropertyChangeLsitener Method on bean " + bean, e);
        } catch (IllegalAccessException e) {
            throw new JavaBeanException(e); //@todo tidy up these with more meaningful messages!
        } catch (InvocationTargetException e) {
            throw new JavaBeanException(e);
        }

    }

    private void removeModelListener(Object bean, String propertyName) throws JavaBeanException {
        try {
            Method addPropertyListener = bean.getClass().getMethod("removePropertyChangeListener", new Class[]{String.class, PropertyChangeListener.class});

            addPropertyListener.invoke(bean, new Object[]{propertyName, this});

        } catch (NoSuchMethodException e) {
            throw new JavaBeanException("Could not find removePropertyChangeLsitener Method on bean " + bean, e);
        } catch (IllegalAccessException e) {
            throw new JavaBeanException(e); //@todo tidy up these with mor meaningful messages!
        } catch (InvocationTargetException e) {
            throw new JavaBeanException(e);
        }

    }

    /**
     * note the use of SwingUtilities here - if the result of setting the value is a big UI update (i.e. changing
     * a selection in a combobox - this will allow the control to finish repainting before the action occurs.
     * Which will preven tunsightly combobox dropdowns remaining open...
     *
     * @param evt
     */
    public void editedValueChanged(String propertyName, Object newValue) {

        if (!_isUpdating && !_editor.isReadonly()) {
//            if (log.isInfoEnabled()) {
//                log.info("editedValueChanged: " + propertyName + " : " + newValue);
//            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    setValueToModel();
                }
            });
        }
    }

    /**
     * note the use of SwingUtilities here - if the result of setting the value is a big UI update (i.e. changing
     * a selection in a combobox - this will allow the control to finish repainting before the action occurs.
     * Which will preven tunsightly combobox dropdowns remaining open...
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (!_isUpdating) {
//            if (log.isInfoEnabled()) {
//                log.info("propertyChange:  " + evt.getPropertyName() + ", value=" + evt.getNewValue() + ", isUpdating = " + _isUpdating + ", " + evt.getSource());
//            }
            if (evt.getSource() == _bean) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setValueToEditor();
                    }
                });
            } else {
                throw new IllegalStateException("The evt was passed to us but we dont know who by!!" + evt); //@todo tidy up this message!!
            }
        }

    }

    private void setValueToEditor() {
        try {
            String val = _modelAdapter.getPropertyValueAsString(_bean, _propertyName, Locale.UK);
            String editorVal = _editor.getValue();
            _isUpdating = true;
            
            if (!editorVal.equals(val)) {
                _editor.setValue(val);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            _isUpdating = false;
        }
    }

    private void reformatEditorValue() {
        _isUpdating = true;
        try {
            _editor.reformatValue(_modelAdapter.getPropertyValueAsString(_bean, _propertyName, Locale.UK));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        _isUpdating = false;
    }

    private void setValueToModel() {
        String val = _editor.getValue();
        try {
            _isUpdating = true;
//            if (log.isInfoEnabled()) {
//                log.info("Setting Value To Model:");
//                log.info("model " + _bean + ", val: " + val + ", name: " + _propertyName);
//            }
            _modelAdapter.setPropertyValueAsString(_bean, _propertyName, val, Locale.UK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            _isUpdating = false;
        }
    }


    public void focusGained(FocusEvent e) {

    }

    public void focusLost(FocusEvent e) {
        if (!_isUpdating) {
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    reformatEditorValue();
                }
            });

        }

    }

    public String toString() {
        return "[binding] : " + _propertyName + " : " + _bean + "->" + _editor;
    }

    private static final Logger log = Logger.getLogger(PropertyBinding.class);
    private Object _bean;
    private String _propertyName;
    private boolean _isUpdating = false;

    private IPropertyValueEditor _editor;
    private IModelAdapter _modelAdapter;
}
