/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryCrossingType extends TypeSafeEnum {


    public static final BoundaryCrossingType ENTRY = new BoundaryCrossingType("entry");
    public static final BoundaryCrossingType EXIT = new BoundaryCrossingType("exit");
    public static final BoundaryCrossingType FIRST_MOVE = new BoundaryCrossingType("firstMove");

    private BoundaryCrossingType(String name) {
        super(name);
    }
}
