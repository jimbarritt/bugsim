/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public interface IDistanceUnit {



    double convertValue(IDistanceUnit distanceUnit, double value);

    String getName();

    String getSymbol();

}
