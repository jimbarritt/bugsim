/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ReleaseFixedLocationStrategy implements IReleaseStrategy, IParameterisedStrategy {


    /**
     * @param strategyP
     * @param params
     * @param simulation
     * @todo decide if simulation or landscape should be passed around all the time.
     */
    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);

        _releaseLocation = ButterflyParameters.getReleaseLocation(strategyP);

        //@todo implement a heading generator which just generates fixed headings.
        if (ButterflyParameters.hasFixedHeading(strategyP)) {
           _releaseHeading = ButterflyParameters.getReleaseHeading(strategyP);
            _generateRandomHeading = false;
        } else {
            _generateRandomHeading = true;
        }
        _simulation = simulation;


    }

    public String getParameterSummary() {
        return "";
    }

    public double generateReleaseI() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double generateBirthMoveLength() {
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ReleaseFixedLocationStrategy() {

    }

    public RectangularCoordinate generateInitialLocation() {
        return _releaseLocation;
    }

    public double generateInitialAzimuth() {
        double h = _releaseHeading;
        if (_generateRandomHeading) {
            h = Geometry.generateUniformRandomAzimuthChange(_simulation.getRandom());
        }
        return h;
    }

    public String toString() {
        return "BirthFixedLocation: " + _releaseLocation;
    }

    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");

    private String _B;
    private Simulation _simulation;

    private RectangularCoordinate _releaseLocation;
    private double _releaseHeading;
    private boolean _generateRandomHeading;
}
