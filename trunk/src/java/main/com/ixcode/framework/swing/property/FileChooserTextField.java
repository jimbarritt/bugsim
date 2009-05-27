/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.*;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo try to work out how to chare the documentListener stuff with the text field one - want to inherit from TextField
 */
public class FileChooserTextField extends JPanel implements ActionListener {

    public FileChooserTextField() {
        this(FileChooserTextField.class,  "startLocation");
    }

    public FileChooserTextField(Class preferencesClass, String prefsStartLocation) {
        super(new BorderLayout());

        _textField = new JTextField("");
        _browseButton = new JButton("...");


        super.add(_textField, BorderLayout.CENTER);
        super.add(_browseButton, BorderLayout.EAST);

        _browseButton.addActionListener(this);
        _preferencesClass = preferencesClass;
        _startLocation = prefsStartLocation;




    }

    public void addActionListener(ActionListener l) {
        _listeners.add(l);
    }

    public void actionPerformed(ActionEvent e) {
        String startLocation = _textField.getText();

        if (startLocation.length() == 0) {
            startLocation = _textField.getText();
        }

        JFileChooser fileChooser = new JFileChooser(startLocation);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(this);

        if (fileChooser.getSelectedFile() != null) {
            File file = fileChooser.getSelectedFile();
            setText(file.getAbsolutePath());
        }
        fireActionPerformedEvent(e);

    }

    private void fireActionPerformedEvent(ActionEvent e) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            ActionListener actionListener = (ActionListener)itr.next();
            actionListener.actionPerformed(e);
        }
    }

    private void setText(String filePath) {
        Preferences prefs = Preferences.userNodeForPackage(_preferencesClass);
        syncPrefs(prefs, filePath);
        _textField.setText(filePath);
    }

    private void syncPrefs(Preferences prefs, String filePath) {
        try {
            prefs.put(_startLocation, filePath);
            prefs.sync();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public void setFilePath(String filePath) {
        _textField.setText(filePath);
    }

    public String getFilePath() {
        return _textField.getText();
    }

    public void setEnabled(boolean enabled) {
        _textField.setEnabled(enabled);
        _browseButton.setEnabled(enabled);
    }

    public JTextField getTextField() {
        return _textField;
    }

    public void initialiseValueFromPreferences() {
        Preferences prefs = Preferences.userNodeForPackage(_preferencesClass);

        String prefsPath = (String)prefs.get(_startLocation, (new File("")).getAbsolutePath());
        setText(prefsPath);

    }

    private Class _preferencesClass = FileChooserTextField.class;



    private JTextField _textField;
    private JButton _browseButton;
    private String _startLocation;
    private List _listeners = new ArrayList();
}
