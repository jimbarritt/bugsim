/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment1b;

import com.ixcode.bugsim.view.experiment.SimulationLandscapeViewFactory;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

import javax.swing.*;
import java.awt.geom.Point2D;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MeanDispersalLandscapeViewFactory extends SimulationLandscapeViewFactory {

    public JFrame initialiseLandscapeView(IExperiment experiment, ExperimentController experimentController) {
        LandscapeFrame f = (LandscapeFrame)super.initialiseLandscapeView(experiment, experimentController);
        LandscapeView view = f.getLandscapeView();
        view.setLogicalGridResolution(20);

        view.setFitToScreen(false);
        view.setZoomPercent(8);
        RectangularCoordinate centre = f.getLandscapeView().getLandscape().getLogicalBounds().getCentre();
        view.setZoomCenter(new Point2D.Double(centre.getDoubleX(), centre.getDoubleY()));

        view.addBackgroundRenderer(new MeanDispersalBackroundRenderer(),0);
        return f;
    }
}
