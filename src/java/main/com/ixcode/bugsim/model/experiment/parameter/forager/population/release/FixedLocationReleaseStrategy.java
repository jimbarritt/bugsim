/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.release;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseFixedLocationStrategy;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 * @deprecated 
 */
public class FixedLocationReleaseStrategy extends ReleaseStrategyBase{

    public FixedLocationReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public FixedLocationReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createStrategyS(RectangularCoordinate location, double heading) {
        StrategyDefinitionParameter fixedLocationS = new StrategyDefinitionParameter(S_FIXED_LOCATION, ReleaseFixedLocationStrategy.class.getName());

        Parameter locationParameter = new Parameter(P_LOCATION, location);
        fixedLocationS.addParameter(locationParameter);

        Parameter headingP = new Parameter(P_HEADING, heading);
        fixedLocationS.addParameter(headingP);

        return fixedLocationS;
    }

    public RectangularCoordinate getLocation() {
        return (RectangularCoordinate)super.getParameter(P_LOCATION).getValue();
    }

    public void setLocation(RectangularCoordinate location) {
        super.getParameter(P_LOCATION).setValue(location);
    }

    public double getHeading() {
        return super.getParameter(P_HEADING).getDoubleValue();
    }

    public void setHeading(double heading) {
        super.getParameter(P_HEADING).setValue(heading);
    }

    public static final String S_FIXED_LOCATION = "fixedLocation";

    public static final String P_HEADING = "heading";
    public static final String P_LOCATION = "location";
}
