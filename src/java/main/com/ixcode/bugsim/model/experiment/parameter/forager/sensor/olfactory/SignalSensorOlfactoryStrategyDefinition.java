/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory;

import com.ixcode.bugsim.model.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.SensorParameters;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.SignalSensor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 5, 2007 @ 3:32:10 PM by jim
 */
public class SignalSensorOlfactoryStrategyDefinition extends OlfactorySensorStrategyBase {

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(100, 90);
    }

    public static StrategyDefinitionParameter createStrategyS(double signalDeltaSnesitivity, double directionDeltaMax) {
        StrategyDefinitionParameter strategyS = new StrategyDefinitionParameter(S_SIGNAL_SENSOR_OLFACTION, SignalSensorOlfactoryStrategy.class.getName());
        strategyS.addParameter(new Parameter(P_SIGNAL_DELTA_SENSITIVITY, signalDeltaSnesitivity));
        strategyS.addParameter(new Parameter(P_DIRECTION_DELTA_MAX, directionDeltaMax));
        SensorParameters.addSensorParameters(strategyS, new ArrayList());


        return strategyS;
    }

    public SignalSensorOlfactoryStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }


    public SignalSensorOlfactoryStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

        initialiseSensors();

    }

    private void initialiseSensors() {

        List strategyDefs = (List)super.getParameter(P_SENSORS).getValue();
        setSensorStrategiesFromParameters(strategyDefs);
    }
    
    public void setSensorStrategiesFromStrategies(List sensorStrategies) {
        List strategyDefinitionParameters = new ArrayList();
        for (Iterator itr = sensorStrategies.iterator(); itr.hasNext();) {
            OlfactorySensorStrategy strategy = (OlfactorySensorStrategy)itr.next();
            strategyDefinitionParameters.add(strategy.getStrategyS());
        }
        _sensorStrategies = new ArrayList(sensorStrategies);
        super.getParameter(P_SENSORS).setValue(strategyDefinitionParameters);
    }


    public void setSensorStrategiesFromParameters(List strategyDefinitionParameters) {
        _sensorStrategies = new ArrayList();
        for (Iterator itr = strategyDefinitionParameters.iterator(); itr.hasNext();) {
            StrategyDefinitionParameter sdp = (StrategyDefinitionParameter)itr.next();
            _sensorStrategies.add(new OlfactorySensorStrategy(sdp, super.getParameterMap(), super.isForwardEvents()));
        }
        super.getParameter(P_SENSORS).setValue(strategyDefinitionParameters);
    }

    public List getSensorStrategies() {
        return _sensorStrategies;
    }

    public List getSensorStrategyDefinitionParameters() {
        return (List)super.getParameter(P_SENSORS).getValue();
    }

    public static List createTwoFortyDegreeSensors(int distanceFromAgent, String signalSurfaceName, double minSensitivity, double maxSensitivity) {
        List sensors = new ArrayList();
        sensors.add(createSensorStrategyP(distanceFromAgent, 320, signalSurfaceName, minSensitivity, maxSensitivity));
        sensors.add(createSensorStrategyP(distanceFromAgent, 40, signalSurfaceName, minSensitivity, maxSensitivity));
        return sensors;
    }

    public static StrategyDefinitionParameter createSensorStrategyP(double distanceFromAgent, double headingFromAgent, String signalSurfaceName, double minSensitivity, double maxSensitivity) {
        StrategyDefinitionParameter sp = new StrategyDefinitionParameter(OlfactorySensorStrategy.S_SIGNAL_SENSOR, SignalSensor.class.getName());
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME, signalSurfaceName));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_DISTANCE_FROM_AGENT, distanceFromAgent));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_HEADING_FROM_AGENT, headingFromAgent));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_MIN_SENSITIVITY, minSensitivity));
        sp.addParameter(new Parameter(OlfactorySensorStrategy.P_MAX_SENSITIVITY, maxSensitivity));
        return sp;
    }

    public double getDirectionDeltaMax() {
        if (!super.hasParameter(P_DIRECTION_DELTA_MAX)) {
            super.getStrategyS().addParameter(new Parameter(P_DIRECTION_DELTA_MAX, 90d));
        }
        return super.getParameter(P_DIRECTION_DELTA_MAX).getDoubleValue();
    }

    public void setDirectionDeltaMax(double delta) {
        if (!super.hasParameter(P_DIRECTION_DELTA_MAX)) {
            super.getStrategyS().addParameter(new Parameter(P_DIRECTION_DELTA_MAX, 90d));
        }
        super.getParameter(P_DIRECTION_DELTA_MAX).setValue(delta);
    }

    public double getSignalDeltaSensitivity() {
        if (!super.hasParameter(P_SIGNAL_DELTA_SENSITIVITY)) {
            super.getStrategyS().addParameter(new Parameter(P_SIGNAL_DELTA_SENSITIVITY, 100d));
        }
        return super.getParameter(P_SIGNAL_DELTA_SENSITIVITY).getDoubleValue();
    }

    public void setSignalDeltaSensitivity(double delta) {
        if (!super.hasParameter(P_SIGNAL_DELTA_SENSITIVITY)) {
            super.getStrategyS().addParameter(new Parameter(P_SIGNAL_DELTA_SENSITIVITY, 100d));
        }
        super.getParameter(P_SIGNAL_DELTA_SENSITIVITY).setValue(delta);
    }


    public static final String S_SIGNAL_SENSOR_OLFACTION = "signalSensorOlfaction";
    public static final String P_SENSORS = "sensors";
    public static final String P_SIGNAL_DELTA_SENSITIVITY = "signalDeltaSensitivity";
    public static final String P_DIRECTION_DELTA_MAX = "directionDeltaMax";
    private List _sensorStrategies;
}
