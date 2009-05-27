/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.javabean.convert;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 12:57:10 PM by jim
 */
public class StringConverter implements IValueConverter {

    public Object convert(Object from) {
        Object converted = null;
        if (from instanceof String) {
            converted = from;
        } else {
            converted = from.toString();
        }
        return converted;
    }
}
