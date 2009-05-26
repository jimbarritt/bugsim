/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanParseException;

import java.util.StringTokenizer;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ScaledDistanceFormat implements IJavaBeanValueFormat {

    public String format(Object value) {
        ScaledDistance d = (ScaledDistance)value;
        return "distance=" + d.getDistance() + " : units=" + d.getUnits().getSymbol();
    }

    public Object parse(String value) throws JavaBeanParseException {
        StringTokenizer st = new StringTokenizer(value, " : ");
        String sDistance = st.nextToken();
        String sUnits = st.nextToken();

        double distance = Double.valueOf(sDistance.substring(sDistance.lastIndexOf("=")+1)).doubleValue();
        IDistanceUnit unit = DistanceUnitRegistry.INSTANCE.resolveUnitFromSymbol(sUnits.substring(sUnits.lastIndexOf("=")+1));
        return new ScaledDistance(distance, unit);
    }
}
