/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.model.experiment.output.SimulationSerialiser;
import com.ixcode.bugsim.view.zoomcontrol.ZoomFrame;
import com.ixcode.framework.swing.action.ActionBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class OpenSimulationAction extends ActionBase {


    public OpenSimulationAction() {
        super("Open Simulation", "/icons/open.gif");

    }

    public void init(LandscapeView view,LandscapeFrame viewFrame, ZoomFrame zoomFrame) {
        _view = view;
        _viewFrame = viewFrame;
        _zoomFrame = zoomFrame;

    }

    /**
     * @param actionEvent
     * @todo Could refactor this into a base class
     */
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            Preferences prefs = Preferences.userNodeForPackage(this.getClass());
            String startLocation = prefs.get(OpenSimulationAction.START_LOCATION, "");

            JFileChooser fileChooser = new JFileChooser(new File(startLocation));
            fileChooser.showOpenDialog(_view);
            if (fileChooser.getSelectedFile() != null) {
                File file = fileChooser.getSelectedFile();
                prefs.put(OpenSimulationAction.START_LOCATION, file.getParentFile().getAbsolutePath());
                prefs.sync();
                _simserialiser.loadSimulation(_view.getLandscape().getSimulation(), file);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private LandscapeView _view;
    private SimulationSerialiser _simserialiser = new SimulationSerialiser();
    private static final String START_LOCATION = "startLocation";
    private LandscapeFrame _viewFrame;
    private ZoomFrame _zoomFrame;

}
