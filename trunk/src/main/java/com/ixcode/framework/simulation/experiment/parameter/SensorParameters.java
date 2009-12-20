/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter;

import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.SignalSensorOlfactoryStrategyDefinition;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class SensorParameters {


    public static double getDistanceFromAgent(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(OlfactorySensorStrategy.P_DISTANCE_FROM_AGENT).getDoubleValue();
    }

    public static double getHeadingFromAgent(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(OlfactorySensorStrategy.P_HEADING_FROM_AGENT).getDoubleValue();
    }

    public static String getSignalSurfaceName(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(OlfactorySensorStrategy.P_SIGNAL_SURFACE_NAME).getStringValue();
    }

    public static double getMinSensitivity(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(OlfactorySensorStrategy.P_MIN_SENSITIVITY).getDoubleValue();
    }

    public static double getMaxSensitivity(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(OlfactorySensorStrategy.P_MAX_SENSITIVITY).getDoubleValue();
    }

    public static boolean getCanSeeBehind(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(CAN_SEE_BEHIND).getBooleanValue();
    }

    public static int getSensorCount(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(SENSOR_COUNT).getIntValue();
    }

    public static List getSensorParams(StrategyDefinitionParameter strategyP) {
        return (List)strategyP.findParameter(SignalSensorOlfactoryStrategyDefinition.P_SENSORS).getValue();
    }

    public static void addSensorParameters(StrategyDefinitionParameter strategyP, List sensorPs) {
        if (strategyP.hasParameter(SignalSensorOlfactoryStrategyDefinition.P_SENSORS)) {
            strategyP.removeParameter(SignalSensorOlfactoryStrategyDefinition.P_SENSORS);
        }
        strategyP.addParameter(new Parameter(SignalSensorOlfactoryStrategyDefinition.P_SENSORS, sensorPs));
    }



    public static List createThreeFortyDegreeSensors(int distanceFromAgent, String signalSurfaceName, double minSensitivity, double maxSensitivity) {
        List sensors = new ArrayList();
        sensors.add(SignalSensorOlfactoryStrategyDefinition.createSensorStrategyP(distanceFromAgent, 320, signalSurfaceName, minSensitivity, maxSensitivity));
        sensors.add(SignalSensorOlfactoryStrategyDefinition.createSensorStrategyP(distanceFromAgent, 0, signalSurfaceName, minSensitivity, maxSensitivity));
        sensors.add(SignalSensorOlfactoryStrategyDefinition.createSensorStrategyP(distanceFromAgent, 40, signalSurfaceName, minSensitivity, maxSensitivity));
        return sensors;

    }



    private static final String CAN_SEE_BEHIND = "canSeeBehind";
    private static final String SENSOR_COUNT = "sensorCount";

}
