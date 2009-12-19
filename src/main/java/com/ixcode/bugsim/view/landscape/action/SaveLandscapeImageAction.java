/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.swing.action.ActionBase;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.view.landscape.*;

import javax.swing.*;
import static javax.imageio.ImageIO.write;
import java.awt.event.ActionEvent;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.log4j.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SaveLandscapeImageAction extends ActionBase {

    private static final Logger log = Logger.getLogger(SaveLandscapeImageAction.class);
    public SaveLandscapeImageAction(LandscapeView view) {
        super("Save Landscape Image", "/icons/snapShot.gif");
        _view = view;
        _controller = view.getExperimentController();


    }

    public void experimentInitialised(IExperiment source, ExperimentPlan plan) {

    }


    public void progressNotification(ExperimentProgress progress) {

    }

    public void actionPerformed(ActionEvent e) {
        saveImage();
    }

    public void saveImage() {
        boolean showGrid = _view.isShowGrid();
        _view.setRenderForPrint(true);
//        _view.setRenderGrids(true);
//        _view.setShowGrid(true);
        _view.redraw();

//        File file = letUserChooseFile();
        File file = generateFile();
        if (file != null) {

            try {
                saveViewAsJPeg(_view, file);
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }

        }
        _view.setRenderForPrint(false);
//        _view.setRenderGrids(true);
//        _view.setShowGrid(showGrid);
        _view.redraw();
    }

    public void saveViewAsJPeg(LandscapeView view, File file) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("Saving landscape image as: " + file.getAbsolutePath());
        }
        int width = view.getWidth();
        int height = view.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        view.paint(g2d);
        g2d.dispose();
        write(bufferedImage, "jpg", file);
    }

    private File generateFile() {
        ExperimentController controller = _view.getExperimentController();
        File root = controller.getExperimentOutputDir();
        File images = new File(root, "output/simulation-images");
        if (!images.exists()) {
            images.mkdirs();
        }

        ExperimentProgress progress = ExperimentController.createExperimentProgress(controller);
        String iteration = F.format(progress.getCurrentIteration());
        String replicate = F.format(progress.getCurrentReplicate());
        String experimentName = controller.getExperiment().getExperimentPlan().getName();
        String experimentId = controller.getExperimentId();
        String trialName = controller.getExperiment().getExperimentPlan().getTrialName();
        String timesteps = F6.format(progress.getTimestepsSinceLastReset());

        String filename = experimentName + "-" + experimentId + "-" + trialName + "-Itr" + iteration + "-Rep" + replicate + "-Ts" + timesteps + ".jpg";
        return new File(images, filename);
    }


    private File letUserChooseFile() {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        String startLocation = prefs.get(START_LOCATION, "");

        JFileChooser fileChooser = new JFileChooser(startLocation);
        fileChooser.showSaveDialog(_view);
        File file = fileChooser.getSelectedFile();

        if (file != null) {
            prefs.put(START_LOCATION, file.getParentFile().getAbsolutePath());
            try {
                prefs.sync();
            } catch (BackingStoreException e2) {
                throw new RuntimeException(e2);
            }
        }
        return file;
    }


    private LandscapeView _view;
    private static final String START_LOCATION = "startLocation";
    DecimalFormat F = new DecimalFormat("000");
    DecimalFormat F6 = new DecimalFormat("000000");
    private ExperimentController _controller;
}
