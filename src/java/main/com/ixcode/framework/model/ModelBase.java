/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.model;

import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : Provides Property Changes support
 */
public abstract class ModelBase implements Serializable{
    protected ModelBase() {
        _changeSupport = new PropertyChangeSupport(this);
    }
    

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _changeSupport.addPropertyChangeListener(l);
        _propertyChangeListeners.add(l);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        _changeSupport.addPropertyChangeListener(propertyName, l);
        _propertyChangeListeners.add(l);
    }




    public void removePropertyChangeListener(PropertyChangeListener l) {
        _changeSupport.removePropertyChangeListener(l);
        _propertyChangeListeners.remove(l);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        _changeSupport.removePropertyChangeListener(propertyName, l);
        _propertyChangeListeners.remove(l);
    }

    public void setPropertyValueAsString(String name, String value) {
        ModelProperty property = getProperty(name);
        if (Integer.class.isAssignableFrom(property.getType())) {
            property.setValue(new Integer(Integer.parseInt(value)));
        } else if (Double.class.isAssignableFrom(property.getType())) {
            property.setValue(new Double(Double.parseDouble(value)));
        } else {
            property.setValue(value);
        }
    }


    protected void firePropertyChangeEvent(PropertyChangeEvent evt) {
        _changeSupport.firePropertyChange(evt);
    }
    protected void firePropertyChangeEvent(String propertyName, long oldValue, long newValue) {
        firePropertyChangeEvent(propertyName, new Long(oldValue), new Long(newValue));

    }

    protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
        _changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

//    protected void propagatePropertyChangeEvent(PropertyChangeEvent e) {
//        for (Iterator itr = _changeSupport.getPropertyChangeListeners().iterator(); itr.hasNext();) {
//            PropertyChangeListener l = (PropertyChangeListener)itr.next();
//            l.propertyChange(e);
//        }
//    }


    protected void addProperty(String name, Class type) {
        addProperty(new ModelProperty(this, name, type));
    }

    protected void addProperty(ModelProperty modelProperty) {
        _properties.put(modelProperty.getName(), modelProperty);
        _propertyNames.add(modelProperty.getName());
    }

    public Object getPropertyValue(String name) {
        return ((ModelProperty)_properties.get(name)).getValue();
    }

    public String getPropertyValueAsString(String name) {
        return ((ModelProperty)_properties.get(name)).getValue().toString();
    }

    public void setPropertyValue(String name, Object value) {
        ModelProperty property = getProperty(name);
        property.setValue(value);
    }

    public double getPropertyDoubleValue(String name) {
        return ((Double)getPropertyValue(name)).doubleValue();
    }

    public void setPropertyDouble(String name, double value) {
        setPropertyValue(name, new Double(value));
    }

    public int getPropertyIntegerValue(String name) {
        return ((Integer)getPropertyValue(name)).intValue();
    }

    public void setPropertyInteger(String name, int value) {
        setPropertyValue(name, new Integer(value));
    }

    public ModelProperty getProperty(String name) {
        return (ModelProperty)_properties.get(name);
    }

    public List getPropertyNames() {
        return _propertyNames;
    }


    private PropertyChangeSupport _changeSupport;

    private static final Logger log = Logger.getLogger(ModelBase.class);
    private Map _properties = new HashMap();
    private List _propertyNames = new ArrayList();
    private boolean _propagatePropertyChangeEvents;
    private List _propertyChangeListeners =  new ArrayList();
}
