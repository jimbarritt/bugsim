/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.SensorParameters;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurface;
import com.ixcode.framework.simulation.model.landscape.information.SignalSample;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class SignalSensor implements IParameterisedStrategy {

    public SignalSensor () {

    }

    public String getParameterSummary() {
        return "";
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        _distanceFromAgent = SensorParameters.getDistanceFromAgent(strategyP);
        _headingFromAgent = SensorParameters.getHeadingFromAgent(strategyP);
        _informationSurfaceName = SensorParameters.getSignalSurfaceName(strategyP);
        _minSensitivity = SensorParameters.getMinSensitivity(strategyP);
        _maxSensitivity = SensorParameters.getMaxSensitivity(strategyP);
    }

    public SignalSensor(double distanceFromAgent, double angleFromAgent, String informationSurfaceName, double minSensitivity, double maxSensitivity) {
        _distanceFromAgent = distanceFromAgent;
        _headingFromAgent = angleFromAgent;
        _informationSurfaceName = informationSurfaceName;
        _minSensitivity = minSensitivity;
        _maxSensitivity = maxSensitivity;
    }

    public SignalSample getInformationSample(IMotileAgent agent, Landscape landscape) {
        ISignalSurface surface = landscape.getInformationSurface(_informationSurfaceName);

        RectangularCoordinate sampleCoordinate = agent.getLocation().getCoordinate().moveTo(new AzimuthCoordinate(getHeading(agent), _distanceFromAgent));
        SignalSample sample = surface.getInformationSample(sampleCoordinate);
        if (DoubleMath.precisionLessThanEqual(sample.getDoubleSignalValue(), _minSensitivity, DoubleMath.DOUBLE_PRECISION_DELTA)) {
            sample = new SignalSample(sampleCoordinate, 0);
        } else if (DoubleMath.precisionGreaterThanEqual(sample.getDoubleSignalValue(),_maxSensitivity, DoubleMath.DOUBLE_PRECISION_DELTA)) {
            sample = new SignalSample(sampleCoordinate, _maxSensitivity);
        }
        return sample;
    }

    public double getHeadingFromAgent() {
        return _headingFromAgent;
    }

    public String toString() {
        return "distance=" + DoubleMath.format(_distanceFromAgent) + ", heading=" + DoubleMath.format(_headingFromAgent);
    }

    public double getDistanceFromAgent() {
        return _distanceFromAgent;
    }

    public double getHeading(IMotileAgent agent) {
        return AzimuthCoordinate.applyAzimuthChange(_headingFromAgent, agent.getAzimuth());
    }

    private double _distanceFromAgent;
    private double _headingFromAgent;
    private String _informationSurfaceName;
    private double _minSensitivity;
    private double _maxSensitivity;
}
