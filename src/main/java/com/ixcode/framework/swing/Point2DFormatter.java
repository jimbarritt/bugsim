/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public final class Point2DFormatter {

    public static final class Double {
        public static Point2D parse(String strValue) {
            String xStr = strValue.substring(1, strValue.indexOf(','));
            String yStr = strValue.substring(strValue.indexOf(',') + 1, strValue.indexOf(")"));
            return new Point2D.Double(java.lang.Double.parseDouble(xStr), java.lang.Double.parseDouble(yStr));
        }

        public static String format(Point2D point2D) {
            return "(" + F2.format(point2D.getX()) + "," + F2.format(point2D.getY()) + ")";
        }
    }

    private static final     NumberFormat F2 = new DecimalFormat("###0.00");
}
