package com.ixcode.framework.xml.sax.speedsax.format;

/**
 */
public class IntegerFormat implements IFormat {

    public IntegerFormat() {
    }

    public Object parseString(String stringValue) {
        return new Integer(Integer.parseInt(stringValue));
    }
}
