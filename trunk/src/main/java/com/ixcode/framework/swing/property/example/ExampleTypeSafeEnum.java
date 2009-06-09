/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property.example;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExampleTypeSafeEnum extends TypeSafeEnum {

    public static final ExampleTypeSafeEnum ENUM_VALUE_1 = new ExampleTypeSafeEnum("value1");
    public static final ExampleTypeSafeEnum ENUM_VALUE_2 = new ExampleTypeSafeEnum("value2");
    public static final ExampleTypeSafeEnum ENUM_VALUE_3 = new ExampleTypeSafeEnum("value3");
    public static final ExampleTypeSafeEnum ENUM_VALUE_4 = new ExampleTypeSafeEnum("value4");

    private ExampleTypeSafeEnum(String name) {
        super(name);
    }

}
