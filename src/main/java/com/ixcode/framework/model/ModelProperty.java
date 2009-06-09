/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.model;

import java.io.Serializable;

/**
 *  Description : Manages a property value in the model
 */
public class ModelProperty implements Serializable {


    public ModelProperty(ModelBase owner, String name, Class type) {
        _name = name;
        _owner = owner;
        _type = type;
    }

    public Object getValue() {
        return _value;
    }

    public void setValue(Object value) {
        Object oldValue  = _value;
        _value = value;
        if (_firePropertyChanged) {
          _owner.firePropertyChangeEvent(_name, oldValue, _value);
        }
    }

    public String getName() {
        return _name;
    }

    public Class getType() {
        return _type;
    }

    private Object _value;
    private boolean _firePropertyChanged = true;
    private String _name;
    private ModelBase _owner;
    private Class _type;
}
