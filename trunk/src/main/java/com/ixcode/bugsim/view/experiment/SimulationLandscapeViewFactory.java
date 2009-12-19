/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.view.landscape.LandscapeViewFactory;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.bugsim.view.agent.AgentRendererRegistry;
import com.ixcode.bugsim.view.agent.MotileAgentRenderer;
import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.framework.simulation.experiment.SimulationExperimentBase;
import com.ixcode.framework.simulation.model.Simulation;

import javax.swing.*;
import java.awt.geom.Point2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimulationLandscapeViewFactory implements LandscapeViewFactory {

    public JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController) {

        if (experiment instanceof ISimulationExperiment) {
            Simulation sim = ((ISimulationExperiment)experiment).getSimulation();
            return initialiseLandscapeView(sim, experiment, experimentController);
        } else {
            throw new IllegalArgumentException("experiment " + experiment + " is not an ISimulationExperiment");
        }

    }

    public boolean isShowLandscapeView() {
        return BugsimMain.isDebug();
    }


    public static LandscapeFrame initialiseLandscapeView(Simulation simulation, IExperiment experiment, ExperimentController experimentController) {

        final LandscapeFrame landscapeFrame = new LandscapeFrame(simulation.getLandscape(), null);
        LandscapeView view = landscapeFrame.getLandscapeView();



        view.setGridResolution(new ScaledDistance(80, DistanceUnitRegistry.centimetres()));
        view.setGridThickness(new ScaledDistance(0.1, DistanceUnitRegistry.centimetres()));
        view.setGridToScale(false);
        view.setListenToAgents(BugsimMain.isDebug());
        view.setExperimentController(experimentController);

        MotileAgentRenderer renderer = (MotileAgentRenderer)AgentRendererRegistry.INSTANCE.getRendererForAgent(ButterflyAgent.AGENT_CLASS_ID);
        renderer.setDrawPaths(BugsimMain.isDebug());


        experiment.addExperimentListener(new IExperimentListener() {

            public void experimentInitialised(IExperiment source, ExperimentPlan plan) {
                Simulation simulation = ((SimulationExperimentBase)source).getSimulation();
                landscapeFrame.setLandscape(simulation.getLandscape());
                LandscapeView view = landscapeFrame.getLandscapeView();
//                    view.setGridResolution(new ScaledDistance(80, DistanceUnitRegistry.centimetres()));
                view.setGridThickness(new ScaledDistance(1, DistanceUnitRegistry.centimetres()));
                view.setListenToAgents(BugsimMain.isDebug());

            }

            public void iterationInitialised(IExperiment source, ParameterMap currentParameters, ExperimentProgress experimentProgress) {
                LandscapeView view = landscapeFrame.getLandscapeView();
                Parameter patch = currentParameters.findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_BOUNDS);
                if (patch != null) {
                    CartesianBounds b = (CartesianBounds)patch.getValue();
                    double patchSize = b.getDoubleWidth();
                    view.setGridResolution(new ScaledDistance(patchSize, DistanceUnitRegistry.centimetres()));
                }
                RectangularCoordinate centre = view.getLandscape().getLogicalBounds().getCentre();
                view.setZoomCenter(new Point2D.Double(centre.getDoubleX(), centre.getDoubleY()));

                landscapeFrame.getStatusBar().setText(source.getParameterSummary()); // @todo put this on the parameters instead or better, pass the parameters to the experiment as it knows what is important
            }

        });
        return landscapeFrame;
    }


}
