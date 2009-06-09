/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ValueCollectionItemXMLFactory {


    public ValueCollectionItemXMLFactory() {
    }


    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        _value = value;
    }

    public String getType() {
        return _type;

    }

    public void setType(String type) {
        _type = type;
        _typeClass = loadClass(type);
    }

    private Class loadClass(String typeClassName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(typeClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public Object createItem(IXMLFormatter xmlFormatter) {
        return xmlFormatter.parseString(_typeClass, _value);        
    }

    private String _value;
    private String _type;
    public static final String P_TYPE = "type";
    public static final String P_VALUE = "value";
    private Class _typeClass;
}
