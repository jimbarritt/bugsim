/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.map;

import com.ixcode.framework.swing.action.ActionBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SaveMapAction extends ActionBase {

    public SaveMapAction(MapImageView view) {
        super("Save Map", "/icons/save.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        String startLocation = prefs.get(START_LOCATION, "");

        JFileChooser fileChooser = new JFileChooser(startLocation);
        fileChooser.showSaveDialog(_view);
        File file = fileChooser.getSelectedFile();
        if (file != null) {
            _view.saveRawImageToFile(file);
            prefs.put(START_LOCATION, file.getParentFile().getAbsolutePath());
            try {
                prefs.sync();
            } catch (BackingStoreException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private MapImageView _view;
    private static final String START_LOCATION = "startLocation";
}
