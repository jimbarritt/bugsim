/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experimentX;

import com.ixcode.bugsim.view.experiment.SimulationLandscapeViewFactory;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.experimentX.ExperimentX;
import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.framework.simulation.model.Simulation;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentXLandscapeViewFactory extends SimulationLandscapeViewFactory {

    public JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController) {
        LandscapeFrame f;
        if (!(ISimulationExperiment.class.isAssignableFrom(experiment.getClass()))) {
            throw new IllegalArgumentException("experiment " + experiment + " is not an ISimulationExperiment");
        }

        Simulation sim = ((ISimulationExperiment)experiment).getSimulation();

        f = initialiseLandscapeView(sim, experiment, experimentController);

        LandscapeView view = f.getLandscapeView();
        double patchSize = view.getLandscape().getExtentX() / 4;
        if (experiment instanceof ExperimentX) {
            ExperimentX exX = (ExperimentX)experiment;
            CartesianBounds b = exX.getCurrentBugsimParameters().getResourceCategory().getResourceLayout().getLayoutBoundary().getBounds();
            patchSize = b.getDoubleWidth();

        }

        double resolution =patchSize; 

//        view.setGridResolution(new ScaledDistance(resolution, view.getLandscape().getScale().getUnits()));
//        view.setGridThickness(new ScaledDistance(0.1, DistanceUnitRegistry.centimetres()));
//        view.setGridToScale(true);
//        



        return f;
    }


}
