/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import junit.framework.TestCase;

import java.awt.geom.Point2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Point2DFormatterTestCase extends TestCase {


    public void testDoubleformat() {
        Point2D point = new Point2D.Double(23.4567d, 68574.4565767787d);
        String actual = Point2DFormatter.Double.format(point);
        assertEquals("doubleFormat", "(23.46,68574.46)", actual);

    }

    public void testDoubleParse() {
        Point2D expected = new Point2D.Double(23.4567d, 68574.4565767787d);
        Point2D actual = Point2DFormatter.Double.parse("(23.4567,68574.4565767787)");
        assertEquals("doubleFormat.parse", expected, actual );

    }


}
