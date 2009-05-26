/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment2a;

import com.ixcode.bugsim.view.experiment.SimulationLandscapeViewFactory;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.framework.simulation.model.Simulation;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment2aLandscapeViewFactory extends SimulationLandscapeViewFactory {

    public JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController) {
        LandscapeFrame f;
        if (!(ISimulationExperiment.class.isAssignableFrom(experiment.getClass()))) {
            throw new IllegalArgumentException("experiment " + experiment + " is not an ISimulationExperiment");
        }

        Simulation sim = ((ISimulationExperiment)experiment).getSimulation();

        f = initialiseLandscapeView(sim, experiment, experimentController);

//        LandscapeView view = f.getLandscapeView(experiment.);
//        view.addOverlayRenderer(new ZeroBoundaryRenderer(), 0);

//        CartesianBounds b = (CartesianBounds)experiment.getCurrentParameters().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_BOUNDS).getValue();
//        double patchSize = b.getDoubleWidth();
        LandscapeView view = f.getLandscapeView();
        view.setGridResolution(new ScaledDistance(80, DistanceUnitRegistry.centimetres()));
        view.setGridThickness(new ScaledDistance(0.1, DistanceUnitRegistry.centimetres()));
        view.setGridToScale(false);


        return f;
    }


}
