/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.javabean.format;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class TypeSafeEnumFormat implements IJavaBeanValueFormat {

    public TypeSafeEnumFormat(Class enumClass) {
        _enumClass = enumClass;
    }

    public Object parse(String value) throws JavaBeanParseException {
        return TypeSafeEnum.resolve(_enumClass, value);
    }

    public String format(Object value) {
        return ((TypeSafeEnum)value).getName();
    }

    private Class _enumClass;
}
