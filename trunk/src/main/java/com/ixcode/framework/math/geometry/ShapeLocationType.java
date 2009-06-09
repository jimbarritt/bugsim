/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 * Description : When a shape has a location it might be specified in different ways, either the centre, topleft tope right etc...
 * Created     : Jan 26, 2007 @ 9:54:57 AM by jim
 */
public class ShapeLocationType extends TypeSafeEnum {

    public static final ShapeLocationType CENTRE = new ShapeLocationType("centre");
    public static final ShapeLocationType TOP_LEFT = new ShapeLocationType("topLeft");
    public static final ShapeLocationType TOP_RIGHT = new ShapeLocationType("topRight");
    public static final ShapeLocationType BOTTOM_LEFT = new ShapeLocationType("bottomLeft");
    public static final ShapeLocationType BOTTOM_RIGHT = new ShapeLocationType("bottomRight");

    private ShapeLocationType(String name) {
        super(name);
    }

}
