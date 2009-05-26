/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CalculatedResourceLayoutType extends TypeSafeEnum {


    public static final CalculatedResourceLayoutType CORNER_EDGE_CENTRE = new CalculatedResourceLayoutType("corner-centre-edge");
    public static final CalculatedResourceLayoutType CORNER_CENTRE = new CalculatedResourceLayoutType("corner-centre");
    

    private CalculatedResourceLayoutType(String name) {
        super(name);
    }


}
