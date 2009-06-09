/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class Kilometres extends DistanceUnitBase {



    Kilometres() {
        super("Kilometres", "km");

        super.addScale(Centimetres.class,  100000);
        super.addScale(Kilometres.class,  1);
        super.addScale(Metres.class,  1000);
        super.addScale(Millimetres.class,  1000000);
    }
}
