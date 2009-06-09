/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.javabean.format.DoubleFormat;
import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanParseException;

import java.util.StringTokenizer;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CartesianBoundsFormat implements IJavaBeanValueFormat {
    public CartesianBoundsFormat() {

    }

    /**
     * @param value
     * @return
     */
    public String format(Object value) {
        CartesianBounds  b = (CartesianBounds)value;
        StringBuffer sb = new StringBuffer();

        sb.append("x=").append(_doubleFormat.format(new Double(b.getDoubleX())));
        sb.append(" : y=").append(_doubleFormat.format(new Double(b.getDoubleY())));
        sb.append(" : w=").append(_doubleFormat.format(new Double(b.getDoubleWidth())));
        sb.append(" : h=").append(_doubleFormat.format(new Double(b.getDoubleHeight())));

        return sb.toString();
    }

    public Object parse(String value) throws JavaBeanParseException {
        StringTokenizer st = new StringTokenizer(value, " : ");

        double x = extractValue(st.nextToken());
        double y = extractValue(st.nextToken());
        double w = extractValue(st.nextToken());
        double h = extractValue(st.nextToken());
        return new CartesianBounds(x, y, w, h);
    }

    private double extractValue(String s) {
        String v = s.substring(s.lastIndexOf("=")+1);
        return Double.valueOf(v).doubleValue();
    }

    private IJavaBeanValueFormat _doubleFormat = new DoubleFormat();


}
