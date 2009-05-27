/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.javabean.convert;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 12:57:10 PM by jim
 */
public class IntegerConverter implements IValueConverter {

    public Object convert(Object from) {
        Object converted = null;
        if (from instanceof String) {
            converted = new Integer(Integer.parseInt((String)from));
        } else if (from instanceof Integer) {
            converted = from;
        }else {
            throw new IllegalStateException("Don't know how to convert '" + from.getClass() + "' to an Integer!");
        }

        return converted;
    }
}
