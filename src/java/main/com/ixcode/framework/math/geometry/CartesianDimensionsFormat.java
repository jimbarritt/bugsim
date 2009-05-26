/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.javabean.format.DoubleFormat;
import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanParseException;

import java.util.StringTokenizer;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CartesianDimensionsFormat implements IJavaBeanValueFormat {
    public CartesianDimensionsFormat() {

    }

    /**
     * @param value
     * @return
     */
    public String format(Object value) {
        CartesianDimensions d = (CartesianDimensions)value;
        StringBuffer sb = new StringBuffer();

        sb.append("w=").append(_doubleFormat.format(new Double(d.getDoubleWidth())));
        sb.append(" : h=").append(_doubleFormat.format(new Double(d.getDoubleHeight())));

        return sb.toString();
    }

    public Object parse(String value) throws JavaBeanParseException {
        StringTokenizer st = new StringTokenizer(value, " : ");

        double w = extractValue(st.nextToken());
        double h = extractValue(st.nextToken());
        return new CartesianDimensions(w, h);
    }

    private double extractValue(String s) {
        String v = s.substring(s.lastIndexOf("=")+1);
        return Double.valueOf(v).doubleValue();
    }

    private IJavaBeanValueFormat _doubleFormat = new DoubleFormat();


}
