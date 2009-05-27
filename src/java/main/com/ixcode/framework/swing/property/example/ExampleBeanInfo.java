/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property.example;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExampleBeanInfo {

    public static final String PROPERTY_DOUBLE_VALUE = "doubleValue";
    public static final String PROPERTY_ENUM_VALUE= "enumValue";
    public static final String PROPERTY_INT_VALUE = "intValue";
    public static final String PROPERTY_PRIMITIVE_DOUBLE_VALUE = "primitiveDoubleValue";
    public static final String PROPERTY_PRIMITIVE_LONG_VALUE = "primitiveLongValue" ;
    public static final String PROPERTY_STRING_VALUE = "stringValue";
    public static final String PROPERTY_INTEGER_VALUE = "integerValue";

    public static final List GROUP_1_PROPERTIES = new ArrayList();
        static {
            GROUP_1_PROPERTIES.add(PROPERTY_DOUBLE_VALUE);
            GROUP_1_PROPERTIES.add(PROPERTY_ENUM_VALUE);
            GROUP_1_PROPERTIES.add(PROPERTY_INT_VALUE);
        }

    public static final List GROUP_2_PROPERTIES = new ArrayList();
        static {
            GROUP_2_PROPERTIES.add(PROPERTY_PRIMITIVE_DOUBLE_VALUE);
            GROUP_2_PROPERTIES.add(PROPERTY_PRIMITIVE_LONG_VALUE);

        }

    public static final List GROUP_3_PROPERTIES = new ArrayList();
        static {
            GROUP_3_PROPERTIES.add(PROPERTY_STRING_VALUE);
            GROUP_3_PROPERTIES.add(PROPERTY_INTEGER_VALUE);

        }
}
