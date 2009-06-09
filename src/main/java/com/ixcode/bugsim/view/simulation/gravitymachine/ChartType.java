/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ChartType extends TypeSafeEnum {

    public static final ChartType BAR_PLOT = new ChartType("barChart");
    public static final ChartType XY_PLOT =  new ChartType("xyPlot");

    public ChartType(String name) {
        super(name);
    }

}
