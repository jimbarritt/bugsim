package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;
import org.xml.sax.Attributes;

import java.lang.reflect.Method;


/**
 */
public class PropertyAttributeMapping  extends PropertyXmlMapping {


    public PropertyAttributeMapping(String propertyName, String attributeName, Class propertyType, Method setterMethod) {
        super(propertyName, propertyType, setterMethod);
        _attributeName = attributeName;
    }

    public String getAttributeName() {
        return _attributeName;
    }

    public void populateProperty(Object instance, String nodeQName, Attributes attributes, IXMLFormatter formatter) {
        String value = attributes.getValue(_attributeName);
        if (value != null) {
            super.setPropertyValueAsString(instance, value, formatter);
        }

    }


    private String _attributeName;

}
