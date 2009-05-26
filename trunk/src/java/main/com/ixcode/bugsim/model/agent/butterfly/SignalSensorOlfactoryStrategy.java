/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IOlfactoryAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.SignalSensor;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.SignalSensorOlfactoryStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SignalSensorOlfactoryStrategy implements IOlfactionStrategy, IParameterisedStrategy {


    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        SignalSensorOlfactoryStrategyDefinition sso = new SignalSensorOlfactoryStrategyDefinition(strategyP, params, false);

        
        List sensorStrategies = sso.getSensorStrategies();
        _sensors = new ArrayList();
        for (Iterator itr = sensorStrategies.iterator(); itr.hasNext();) {
            OlfactorySensorStrategy strategy = (OlfactorySensorStrategy)itr.next();
            SignalSensor sensor = (SignalSensor)strategy.instantiateImplementedStrategy(initialisationObjects);
            _sensors.add(sensor);
        }




        _directionDeltaMax = sso.getDirectionDeltaMax();
        _signalDeltaSensitivity = sso.getSignalDeltaSensitivity();

    }

    public String getParameterSummary() {
        return "";
    }

    public SignalSensorOlfactoryStrategy() {
    }


    public List getSensors() {
        return _sensors;
    }

    public static List getSensorsFromAgent(IMotileAgent agent) {
        if (!(agent instanceof IOlfactoryAgent)) {
            throw new IllegalStateException("Agent must implement " + IOlfactoryAgent.class.getName() + " to work with this class " + SignalSensorOlfactoryStrategy.class.getName());
        }
        IOlfactionStrategy olfactionS = ((IOlfactoryAgent)agent).getOlfactionStrategy();
        if (!(olfactionS instanceof SignalSensorOlfactoryStrategy)) {
            throw new IllegalStateException("Cannot deal with an olfaction strategy: " + olfactionS.getClass().getName());
        }


        List sensors = ((SignalSensorOlfactoryStrategy)olfactionS).getSensors();
        return sensors;
    }

    public double getDirectionDeltaMax() {
        return _directionDeltaMax;
    }

    public void setOlfactionEnabled(boolean olfactionEnabled) {
        _olfactionEnabled = olfactionEnabled;
    }


    public double getSignalDeltaSensitivity() {
        return _signalDeltaSensitivity;
    }

    private List _sensors;

    private double _directionDeltaMax;
    private boolean _olfactionEnabled;
    private double _signalDeltaSensitivity;
}
