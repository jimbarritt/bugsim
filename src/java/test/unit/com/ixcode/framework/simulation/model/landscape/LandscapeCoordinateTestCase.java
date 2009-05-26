/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import junit.framework.TestCase;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeCoordinateTestCase extends TestCase {


    public void testEquals() {
        RectangularCoordinate c1 = new RectangularCoordinate(0.234d, 0.456d);
        RectangularCoordinate c2 = new RectangularCoordinate(0.234d, 0.456d);
        assertEquals("Equal Coordinates", c1, c2);
    }

    public void testNotEquals_1() {
        RectangularCoordinate c1 = new RectangularCoordinate(0.234d, 0.456d);
        RectangularCoordinate c2 = new RectangularCoordinate(0.235d, 0.456d);
        assertFalse("Not Equal Coordinates", c1.equals(c2));
    }

    public void testNotEquals_2() {
        RectangularCoordinate c1 = new RectangularCoordinate(0.234d, 0.456d);
        RectangularCoordinate c2 = new RectangularCoordinate(0.234d, 0.457d);
        assertFalse("Not Equal Coordinates", c1.equals(c2));
    }

    public void testHashCode() {
        RectangularCoordinate c1 = new RectangularCoordinate(0.234d, 0.456d);
        RectangularCoordinate c2 = new RectangularCoordinate(0.234d, 0.456d);
        assertEquals("Equal Coordinates", c1.hashCode(), c2.hashCode());
    }
}
