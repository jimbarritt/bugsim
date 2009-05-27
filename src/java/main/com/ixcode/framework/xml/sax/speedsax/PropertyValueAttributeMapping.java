/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;
import org.xml.sax.Attributes;

import java.lang.reflect.Method;

/**
 * Description : Allows you to set a generic property by using 2 attributes - one which has the value and one which tells you the type.
 * You will of course need an appropriate format for converting the String value to the property Type.
 */
public class PropertyValueAttributeMapping extends PropertyAttributeMapping {

    public PropertyValueAttributeMapping(String propertyName, String valueAttributeName, String typeAttributeName, Method setterMethod) {
        super(propertyName, valueAttributeName, null, setterMethod);
        _typeAttributeName = typeAttributeName;

    }

    public String getTypeAttributeName() {
        return _typeAttributeName;
    }

    /**
     * If it can't find the value type attribute it just skips trying to set the property.
     *
     * Could throw an exception but it means you have to be more strict on the XML specification and you might not want to be
     * @param instance
     * @param nodeQName
     * @param attributes
     * @param formatter
     */
    public void populateProperty(Object instance, String nodeQName, Attributes attributes, IXMLFormatter formatter) {
        String propertyClassName = attributes.getValue(_typeAttributeName);
        if (propertyClassName != null) {
            Class propertyType = loadClass(propertyClassName);
            String value = attributes.getValue(super.getAttributeName());
            super.setPropertyValueAsString(instance, value, propertyType, formatter);
        }

    }

    private Class loadClass(String className) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ClassNotFoundException: " + className + e);
        }
    }

    private String _typeAttributeName;
}
