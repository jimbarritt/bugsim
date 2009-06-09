/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Millimetres extends DistanceUnitBase {


     Millimetres() {
        super("Millimetres", "mm");

        super.addScale(Centimetres.class,  .1);
        super.addScale(Kilometres.class,  .000001);
        super.addScale(Metres.class,  0.001);
        super.addScale(Millimetres.class,  1);
    }
}
