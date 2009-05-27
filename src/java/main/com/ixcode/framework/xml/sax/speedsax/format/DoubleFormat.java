package com.ixcode.framework.xml.sax.speedsax.format;

/**
 */
public class DoubleFormat implements IFormat {

    public Object parseString(String stringValue) {
        return Double.valueOf(stringValue);
    }
}
