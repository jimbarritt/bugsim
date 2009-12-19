/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.map;

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
public class OpenMapAction extends ActionBase {

    public OpenMapAction(MapImageView view) {
        super("Open Map", "/icons/open.gif");
        _view = view;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        try {
            Preferences prefs = Preferences.userNodeForPackage(this.getClass());
            String startLocation = prefs.get(START_LOCATION, "");

            JFileChooser fileChooser = new JFileChooser(new File(startLocation));
            fileChooser.showOpenDialog(_view);
            if (fileChooser.getSelectedFile() != null) {
                File file = fileChooser.getSelectedFile();

                _view.loadImageFromFileName(file);
                prefs.put(START_LOCATION, file.getParentFile().getAbsolutePath());
                prefs.sync();
                
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }

    private MapImageView _view;
    private static final String START_LOCATION = "startLocation";
}
