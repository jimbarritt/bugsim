/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.framework.swing.action.ActionBase;
import com.ixcode.bugsim.view.landscape.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class OpenBackgroundImageAction extends ActionBase {

    public OpenBackgroundImageAction(LandscapeView view) {
        super("Open Background Image", "/icons/open.gif");
        _view = view;
    }

    /**
     * @todo Could refactor this into a base class
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
       try {
            Preferences prefs = Preferences.userNodeForPackage(this.getClass());
            String startLocation = prefs.get(START_LOCATION, "");

            JFileChooser fileChooser = new JFileChooser(new File(startLocation));
            fileChooser.showOpenDialog(_view);
            if (fileChooser.getSelectedFile() != null) {
                File file = fileChooser.getSelectedFile();

                _view.loadBackgroundImageFromFileName(file);
                prefs.put(START_LOCATION, file.getParentFile().getAbsolutePath());
                prefs.sync();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
    }

    private LandscapeView _view
            ;
    private static final String START_LOCATION = "startLocation";
}
