/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanParseException;

import java.util.StringTokenizer;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class RectangularCoordinateFormat implements IJavaBeanValueFormat{

    public String format(Object value) {
        RectangularCoordinate  b = (RectangularCoordinate)value;
        return "x:" + b.getDoubleX() + " y:" + b.getDoubleY() ;
    }

    public Object parse(String value) throws JavaBeanParseException {
        StringTokenizer st = new StringTokenizer(value, " ");
        double x = extractValue(st.nextToken());
        double y = extractValue(st.nextToken());

        return new RectangularCoordinate(x, y);
    }

    private double extractValue(String s) {
        String v = s.substring(s.lastIndexOf(":")+1);
        return Double.valueOf(v).doubleValue();
    }


}