/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DirectionOfChange extends TypeSafeEnum {

    public static final DirectionOfChange CLOCKWISE = new DirectionOfChange("clockwise");
    public static final DirectionOfChange ANTI_CLOCKWISE = new DirectionOfChange("anti-clockwise");
    public static final DirectionOfChange NONE = new DirectionOfChange("none");

    private DirectionOfChange(String name) {
        super(name);

    }


}
