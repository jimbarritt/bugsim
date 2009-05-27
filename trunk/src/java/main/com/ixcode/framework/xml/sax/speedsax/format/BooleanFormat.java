package com.ixcode.framework.xml.sax.speedsax.format;

/**
 */
public class BooleanFormat implements IFormat {

    public Object parseString(String stringValue) {
        return Boolean.valueOf(stringValue);
    }
}
