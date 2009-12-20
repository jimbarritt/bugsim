/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter.resource.layout.predefined;

import com.ixcode.framework.javabean.format.DoubleFormat;
import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.javabean.format.LongFormat;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.CabbageInitialisationParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CabbageInitialisationParametersFormat implements IJavaBeanValueFormat {

    public String format(Object value) {
        CabbageInitialisationParameters params = (CabbageInitialisationParameters)value;
        StringBuffer sb = new StringBuffer();
        sb.append("id=").append(_longFormat.format(new Long(params.getCabbageId())));
        sb.append(" : radius=").append(_doubleFormat.format(new Double(params.getRadius())));
        sb.append(" : x=").append(_doubleFormat.format(new Double(params.getX())));
        sb.append(" : y=").append(_doubleFormat.format(new Double(params.getY())));

        return sb.toString();

    }

    /**
     * @todo this could be made into a generic formatter for properties given the names and types....
     * @param value
     * @return
     * @throws JavaBeanParseException
     */
    public Object parse(String value) throws JavaBeanParseException {
        Map propertyValues = new HashMap();
        StringTokenizer st = new StringTokenizer(value, " : ");

        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            addPropertyValue(token, propertyValues);
        }

        long id = ((Long)_longFormat.parse((String)propertyValues.get("id"))).longValue();
        double radius = ((Double)_doubleFormat.parse((String)propertyValues.get("radius"))).doubleValue();
        double x = ((Double)_doubleFormat.parse((String)propertyValues.get("x"))).doubleValue();
        double y = ((Double)_doubleFormat.parse((String)propertyValues.get("y"))).doubleValue();



        return new CabbageInitialisationParameters(id, new RectangularCoordinate(x, y), radius);
    }

    private void addPropertyValue(String nameValueString, Map propertyValues) {
        StringTokenizer st = new StringTokenizer(nameValueString, "=");
        String name = st.nextToken();
        String value = st.nextToken();
        propertyValues.put(name, value);
    }

    private IJavaBeanValueFormat _doubleFormat = new DoubleFormat();
    private IJavaBeanValueFormat _longFormat = new LongFormat();
}
