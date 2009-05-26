/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Metres extends DistanceUnitBase {


     Metres() {
        super("Metres", "m");

        super.addScale(Centimetres.class,  .01);
        super.addScale(Kilometres.class,  1000);
        super.addScale(Metres.class,  1);
        super.addScale(Millimetres.class,  0.001);
    }
}
