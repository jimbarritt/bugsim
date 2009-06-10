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
        try {
            return TypeSafeEnum.resolve(_enumClass, value);
        } catch (Throwable t) {
            throw new JavaBeanParseException("foobar", "Could not parse TypeSafeEnum value[{0}] for enum class " + _enumClass.getName() + "]",  value, t);
        }
    }

    public String format(Object value) {
        return ((TypeSafeEnum)value).getName();
    }

    private Class _enumClass;
}
