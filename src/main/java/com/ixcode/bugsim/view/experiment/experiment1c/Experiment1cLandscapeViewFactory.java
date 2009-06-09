/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment1c;

import com.ixcode.bugsim.model.experiment.IBugsimExperiment;
import com.ixcode.bugsim.view.experiment.SimulationLandscapeViewFactory;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.bugsim.view.landscape.zoomcontrol.ZoomFrame;
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
public class Experiment1cLandscapeViewFactory extends SimulationLandscapeViewFactory {

    public JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController) {
        LandscapeFrame f;
        if (!(experiment instanceof IBugsimExperiment)) {
            throw new IllegalArgumentException("experiment " + experiment + " is not an IBugsimExperiment");
        }

        Simulation sim = ((ISimulationExperiment)experiment).getSimulation();

        f = initialiseLandscapeView(sim, experiment, experimentController );

//        LandscapeView view = f.getLandscapeView(experiment.);
//        view.addOverlayRenderer(new ZeroBoundaryRenderer(), 0);


//        CartesianBounds b = (CartesianBounds)experiment.getCurrentParameters().findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_BOUNDS).getValue();
//        double patchSize = b.getDoubleWidth();
      LandscapeView view = f.getLandscapeView();
          view.setGridResolution(new ScaledDistance(80, DistanceUnitRegistry.centimetres()));
          view.setGridThickness(new ScaledDistance(0.1, DistanceUnitRegistry.centimetres()));
          view.setGridToScale(false);

        ZoomFrame zoom = new ZoomFrame(view);
        zoom.setSize(200, 200);
        zoom.show();

        return f;
    }


}
