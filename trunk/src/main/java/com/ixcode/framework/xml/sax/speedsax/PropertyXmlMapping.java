package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 */
public class PropertyXmlMapping {

    public PropertyXmlMapping(String propertyName, Class propertyType, Method setterMethod) {
        _propertyName = propertyName;
        _propertyType = propertyType;
        _setterMethod = setterMethod;
    }

    public String getPropertyName() {
        return _propertyName;
    }

    public Class getPropertyType() {
        return _propertyType;
    }

    public Method getSetterMethod() {
        return _setterMethod;
    }

    public void setPropertyValueAsString(Object instance, String stringValue, IXMLFormatter formatter) {
        setPropertyValueAsString(instance, stringValue, _propertyType, formatter);
    }

    protected void setPropertyValueAsString(Object instance, String stringValue, Class propertyType, IXMLFormatter formatter) {
        Object value = formatter.parseString(propertyType,stringValue);
        try {
            _setterMethod.invoke(instance, new Object[] {value});
        } catch (IllegalAccessException e) {
            throw new NodeProcessingException("Could not set value '" + stringValue + "' into property '" + _propertyName + "' on object of class '" + _setterMethod.getDeclaringClass() + "' : " + e.getMessage(), e );
        } catch (InvocationTargetException e) {
            throw new NodeProcessingException("Could not set value '" + stringValue + "' into property '" + _propertyName + "' on object of class '" + _setterMethod.getDeclaringClass() + "' : " + e.getTargetException().getMessage(),e );
        }
    }

    /**
     * Not all property mappings need to actually get populated, you can set the property name to null
     * if you dont want it populated.     
     */
    boolean hasProperty() {
        return _propertyName != null;
    }

    private String _propertyName;
    private Class _propertyType;
    private Method _setterMethod;

}
