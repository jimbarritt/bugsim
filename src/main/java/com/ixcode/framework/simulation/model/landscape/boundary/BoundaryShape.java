/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryShape extends TypeSafeEnum {


    public static final BoundaryShape RECTANGULAR = new BoundaryShape("rectangular");
    public static final BoundaryShape CIRCULAR = new BoundaryShape("circular");
    public static final BoundaryShape LINEAR = new BoundaryShape("linear");

    public BoundaryShape(String name) {
        super(name);
    }

    public boolean isLinear() {
        return this == LINEAR;
    }

    public boolean isCircular() {
        return this == CIRCULAR;
    }

    public boolean isRectangular() {
        return this == RECTANGULAR;
    }

    public boolean is(BoundaryShape shape) {
        return this == shape;
    }
}
