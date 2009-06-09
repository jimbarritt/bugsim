/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DistributionType extends TypeSafeEnum {


    public static final DistributionType INNER = new DistributionType("inner");
    public static final DistributionType OUTER = new DistributionType("outer");

    protected DistributionType(String name) {
        super(name);
    }


    public String getId() {
        return super.getName();
    }





}
