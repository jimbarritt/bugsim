/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.datatype;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class IxDataTypeUtils {
    public static boolean isNumeric(Class type) {
        if (Integer.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type)) {
            return true;
        }
        return false;
    }

    private static final Map NUMERIC_TYPES = new HashMap();
}
