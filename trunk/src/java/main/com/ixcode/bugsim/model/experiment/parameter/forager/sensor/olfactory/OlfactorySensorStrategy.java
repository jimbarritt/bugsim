/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.SignalSensor;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 6, 2007 @ 1:51:23 PM by jim
 */
public class OlfactorySensorStrategy extends StrategyDefinition {

    public static StrategyDefinitionParameter createSensorStrategyS(double distanceFromAgent, double headingFromAgent, String signalSurfaceName, double minSensitivity, double maxSensitivity) {
        StrategyDefinitionParameter sp = new StrategyDefinitionParameter(OlfactorySensorStrategy.S_SIGNAL_SENSOR, SignalSensor.class.getName());
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME, signalSurfaceName));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_DISTANCE_FROM_AGENT, distanceFromAgent));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_HEADING_FROM_AGENT, headingFromAgent));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_MIN_SENSITIVITY, minSensitivity));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_MAX_SENSITIVITY, maxSensitivity));
        return sp;
    }

    public OlfactorySensorStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }

    public OlfactorySensorStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getDistanceFromAgent() {
        return super.getParameter(P_DISTANCE_FROM_AGENT).getDoubleValue();
    }

    public double getHeadingFromAgent() {
        return super.getParameter(OlfactorySensorStrategy.P_HEADING_FROM_AGENT).getDoubleValue();
    }

    public String getSignalSurfaceName() {
        return super.getParameter(OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME).getStringValue();
    }

    public double getMinSensitivity() {
        return super.getParameter(OlfactorySensorStrategy.P_MIN_SENSITIVITY).getDoubleValue();
    }

    public double getMaxSensitivity() {
        return super.getParameter(OlfactorySensorStrategy.P_MAX_SENSITIVITY).getDoubleValue();
    }


    public void setDistanceFromAgent(double distance) {
        super.getParameter(P_DISTANCE_FROM_AGENT).setValue(distance);
    }

    public void setHeadingFromAgent(double heading) {
        super.getParameter(OlfactorySensorStrategy.P_HEADING_FROM_AGENT).setValue(heading);
    }

    public void setSignalSurfaceName(String surfaceName) {
        super.getParameter(OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME).setValue(surfaceName);
    }

    public void setMinSensitivity(double minSenxitivity) {
        super.getParameter(OlfactorySensorStrategy.P_MIN_SENSITIVITY).setValue(minSenxitivity);
    }

    public void setMaxSensitivity(double maxSensitivity) {
        super.getParameter(OlfactorySensorStrategy.P_MAX_SENSITIVITY).setValue(maxSensitivity);
    }

    public static final String S_SIGNAL_SENSOR = "signalSensor";

    public static final String P_DISTANCE_FROM_AGENT = "distanceFromAgent";
    public static final String P_HEADING_FROM_AGENT = "headingFromAgent";
    public static final String P_SIGNAL_SURFACE_NAME = "signalSurfaceName";
    public static final String P_MIN_SENSITIVITY = "minSensitivity";
    public static final String P_MAX_SENSITIVITY = "maxSensitivity";

}
