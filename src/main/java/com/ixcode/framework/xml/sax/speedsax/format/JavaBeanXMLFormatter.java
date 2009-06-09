/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.xml.sax.speedsax.format;

import com.ixcode.framework.javabean.format.JavaBeanFormatter;
import com.ixcode.framework.javabean.format.JavaBeanParseException;

import java.util.Locale;

/**
 *  Description : Adapts the JavaBeanFormatter for use in the XML parsing framework.
 */
public class JavaBeanXMLFormatter implements IXMLFormatter {

    public JavaBeanXMLFormatter() {
    }

    public JavaBeanXMLFormatter(JavaBeanFormatter javabeanFormatter) {
        _javabeanFormatter = javabeanFormatter;
    }

    public Object parseString(Class parseType, String stringValue) {
        try {
            return _javabeanFormatter.getFormat(Locale.UK,parseType ).parse(stringValue);
        } catch (JavaBeanParseException e) {
            throw new RuntimeException(e);
        }
    }

    public JavaBeanFormatter getJavabeanFormatter() {
        return _javabeanFormatter;
    }

    private JavaBeanFormatter _javabeanFormatter = new JavaBeanFormatter();
}
