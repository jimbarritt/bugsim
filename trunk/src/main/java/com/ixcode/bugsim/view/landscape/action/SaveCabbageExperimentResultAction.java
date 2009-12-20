/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.bugsim.model.experiment.output.CabbageExperimentResultWriter;
import com.ixcode.bugsim.model.experiment.output.SimulationResultWriter;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.swing.action.ActionBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class SaveCabbageExperimentResultAction extends ActionBase {

    public SaveCabbageExperimentResultAction(LandscapeView view, String experimentalGridName) {
        super("Save Simulation Results", "/icons/save.gif");
        _view = view;
        _writer = new CabbageExperimentResultWriter(experimentalGridName);
    }


    public void actionPerformed(ActionEvent e) {
       Simulation simulation =  null;//_view.getSimulation();

         Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        String startLocation = prefs.get(SaveCabbageExperimentResultAction.START_LOCATION, "");

        JFileChooser fileChooser = new JFileChooser(startLocation);
        fileChooser.showSaveDialog(_view);
        File file = fileChooser.getSelectedFile();
        if (file != null) {
            prefs.put(SaveCabbageExperimentResultAction.START_LOCATION, file.getParentFile().getAbsolutePath());
            try {
                prefs.sync();
            } catch (BackingStoreException e2) {
                throw new RuntimeException(e2);
            }
            try {
                _writer.saveCabbageExperimentResults(simulation, file);
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }

        }
    }

    private SimulationResultWriter _writer;

    private LandscapeView _view;
    private static final String START_LOCATION = "startLocation";
}
