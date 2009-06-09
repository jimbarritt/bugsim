/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.javabean.convert;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 12:57:10 PM by jim
 */
public class LongConverter implements IValueConverter {

    public Object convert(Object from) {
        Object converted = null;
        if (from instanceof String) {
            converted = new Long(Long.parseLong((String)from));
        } else if (from instanceof Long) {
            converted = from;
        }else {
            throw new IllegalStateException("Don't know how to convert '" + from.getClass() + "' to a Long!");
        }

        return converted;
    }
}
