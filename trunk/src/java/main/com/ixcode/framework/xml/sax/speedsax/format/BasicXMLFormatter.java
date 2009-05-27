package com.ixcode.framework.xml.sax.speedsax.format;

import com.ixcode.framework.xml.sax.speedsax.NodeProcessingException;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class BasicXMLFormatter implements IXMLFormatter {

    public BasicXMLFormatter() {
        addFormat(_formats, Integer.class, new IntegerFormat());
        addFormat(_formats, int.class, new IntegerFormat());
        addFormat(_formats, String.class, new StringFormat());
        addFormat(_formats, Boolean.class, new BooleanFormat());
        addFormat(_formats, boolean.class, new BooleanFormat());
        addFormat(_formats, Double.class, new DoubleFormat());
        addFormat(_formats, double.class, new DoubleFormat());
    }

    public Object parseString(Class parseType, String stringValue) {
        Object parsedValue = null;
        if (stringValue != null && stringValue.length() > 0) {
            parsedValue = getFormat(parseType).parseString(stringValue);
        }
        return parsedValue;
    }

    private IFormat getFormat(Class parseType) {
        if (!_formats.containsKey(parseType.getName())) {
            throw new NodeProcessingException("No format for parseType '" + parseType.getName() + "'");
        }
        return (IFormat)_formats.get(parseType.getName());
    }

    private static void addFormat(Map formats, Class formatType, IFormat format) {
        formats.put(formatType.getName(), format);
    }

    private Map _formats = new HashMap();


}
