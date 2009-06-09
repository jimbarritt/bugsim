/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;



/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Centimetres extends DistanceUnitBase {

    private static final String NAME = "Centimetres";

    Centimetres() {
        super(NAME, "cm");
        super.addScale(Centimetres.class,  1);
        super.addScale(Kilometres.class,  1000);
        super.addScale(Metres.class,  100);
        super.addScale(Millimetres.class,  .1);
    }


}
