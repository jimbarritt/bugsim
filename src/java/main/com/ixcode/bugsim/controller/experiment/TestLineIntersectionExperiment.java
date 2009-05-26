/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.OpenSimulationAction;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated
 */
public class TestLineIntersectionExperiment extends ExperimentBase {

    public TestLineIntersectionExperiment(Simulation simulation, OpenSimulationAction openSimulationAction) {
        super("Test Line Intersection Experiment", simulation, openSimulationAction,Long.MAX_VALUE, 100, null, null);
    }

    protected void customiseView(LandscapeFrame frame) {
        frame.getLandscapeView().setFitToScreen(false);
        frame.getLandscapeView().setZoomPercent(3);
        frame.getLandscapeView().setGridResolution(new ScaledDistance(10, DistanceUnitRegistry.centimetres()));
        frame.getLandscapeView().setGridThickness(new ScaledDistance(1, DistanceUnitRegistry.centimetres()));
    }

    public void iterationCompleted(Simulation simulation) {

    }


    protected String initialiseExperiment(Simulation simulation, ExperimentProperties parameters) {
        CabbageAgent cabbage = new CabbageAgent(1, new Location(195, 195), 10);
        simulation.addAgent(cabbage);
        return null;
    }
}
