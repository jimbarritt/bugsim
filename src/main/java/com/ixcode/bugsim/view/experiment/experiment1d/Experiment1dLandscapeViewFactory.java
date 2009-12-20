package com.ixcode.bugsim.view.experiment.experiment1d;

import com.ixcode.bugsim.view.experiment.SimulationLandscapeViewFactory;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.bugsim.view.zoomcontrol.ZoomFrame;
import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.framework.simulation.model.Simulation;

import javax.swing.*;

public class Experiment1dLandscapeViewFactory extends SimulationLandscapeViewFactory {

    public JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController) {
        LandscapeFrame f;
        if (!(experiment instanceof ISimulationExperiment)) {
            throw new IllegalArgumentException("experiment " + experiment + " is not an ISimulationExperiment");
        }

        Simulation sim = ((ISimulationExperiment)experiment).getSimulation();

        f = initialiseLandscapeView(sim, experiment, experimentController);

//          LandscapeView view = f.getLandscapeView();
//          view.setGridResolution(new ScaledDistance(20, DistanceUnitRegistry.centimetres()));
//          view.setGridThickness(new ScaledDistance(0.1, DistanceUnitRegistry.centimetres()));
//          view.setGridToScale(true);

//        ZoomFrame zoom = new ZoomFrame(view);
//        zoom.setSize(200, 200);
//        zoom.show();

        return f;
    }


}
