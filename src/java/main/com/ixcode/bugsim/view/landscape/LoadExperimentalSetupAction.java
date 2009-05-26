/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.model.experiment.input.CabbageLoader;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
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
public class LoadExperimentalSetupAction extends ActionBase {

    public LoadExperimentalSetupAction(LandscapeView view) {
        super("Load Experiment Setup", "/icons/open.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent e) {
        // @todo allow user to choose which grid to use as a coordinate space
        Grid coordinateSpace = (Grid)_view.getLandscape().getGrids().get(0);

        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        String startLocation = prefs.get(LoadExperimentalSetupAction.START_LOCATION, "");

        JFileChooser fileChooser = new JFileChooser(new File(startLocation));
        fileChooser.showOpenDialog(_view);
        if (fileChooser.getSelectedFile() != null) {
            File file = fileChooser.getSelectedFile();
            prefs.put(LoadExperimentalSetupAction.START_LOCATION, file.getParentFile().getAbsolutePath());
            try {
                prefs.sync();
            } catch (BackingStoreException x) {
                throw new RuntimeException(x);
            }
            File importFile = fileChooser.getSelectedFile();
            CabbageLoader loader = new CabbageLoader();
            RectangularCoordinate origin = coordinateSpace.getLandscapeOrigin();
            try {
                loader.loadCabbages(_view.getLandscape(), origin, importFile);
            } catch (IOException x) {
                throw new RuntimeException(x);
            }
        }


    }

    private LandscapeView _view;
    private static final String START_LOCATION = "startLocation";
}
