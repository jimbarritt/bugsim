/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.javabean.format.JavaBeanParseException;
import junit.framework.TestCase;

/**
 * TestCase for class : CartesianBoundsFormat
 */
public class CartesianBoundsFormatTestCase extends TestCase {

    public void testFormat() {
        CartesianBounds b = new CartesianBounds(10, 20, 30, 40);
        String actual = _format.format(b);
        String expected = "x:10.0 y:20.0 w:30.0 h:40.0";
        assertEquals(expected, actual);
    }


    public void testParse() throws JavaBeanParseException {
        CartesianBounds b = (CartesianBounds)_format.parse("x:10.0 y:20.0 w:30.0 h:40.0");
        assertEquals(10.0, b.getDoubleX(), 0.0000001);
        assertEquals(20.0, b.getDoubleY(), 0.0000001);
        assertEquals(30.0, b.getDoubleWidth(), 0.0000001);
        assertEquals(40.0, b.getDoubleHeight(), 0.0000001);
    }
    private CartesianBoundsFormat _format = new CartesianBoundsFormat();

}
