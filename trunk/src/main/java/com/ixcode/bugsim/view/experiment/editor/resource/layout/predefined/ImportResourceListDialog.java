/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceListFile;
import com.ixcode.bugsim.view.landscape.geometry.DistanceUnitComboBox;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.FileChooserPropertyEditor;
import com.ixcode.framework.swing.property.GenericNameValuePropertyEditor;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ImportResourceListDialog extends JDialog {

    public ImportResourceListDialog(JFrame owner) throws HeadlessException {
        super(owner);
        init();
    }
    public ImportResourceListDialog(JDialog owner) throws HeadlessException {
        super(owner);

        init();

    }

    private void init() {
        super.setModal(true);


        super.setTitle("Import Resource List ...");

        initUI();

        initRecentPlans();
        updateBrowseFiles();

        _tabbedPane.setSelectedIndex(1);

        super.setSize(800, 400);
        JFrameExtension.centreWindowOnScreen(this);
    }

    private void updateBrowseFiles() {
        try {
            List resourceListFiles;
            if (_directoryChooser.getValue() != null && _directoryChooser.getValue().length() > 0) {
                resourceListFiles = ResourceListFile.readResourceListFiles(new File(_directoryChooser.getValue()));
            } else {
                resourceListFiles = new ArrayList();
            }
            _browseResourceFilesTable.setResourceListFiles(resourceListFiles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initRecentPlans() {
        java.util.List recentPlans = new ArrayList();
//        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrX.xml", "Test Sensory Perception Experiment"));
//        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrA.xml", "Blah blah bla"));
//        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrB.xml", "Some more nonsens"));
//        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrC.xml", "And some more!!"));

        _recentPlanTable.setResourceListFiles(recentPlans);
        _recentPlanTable.getTable().getSelectionModel().addSelectionInterval(0, 0);

    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());

        _tabbedPane = createTabbedPane();
        container.add(_tabbedPane, BorderLayout.CENTER);

        _titlePane = createTitlePane();
        container.add(_titlePane, BorderLayout.NORTH);

        _buttonPanel = createButtonPanel();
        container.add(_buttonPanel, BorderLayout.SOUTH);

        super.getContentPane().add(container);
    }

    private JPanel createTitlePane() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        _selectedResourceListFileEditor = new ReadOnlyPropertyEditor("currenPlan", "Selected Plan", 100);
        _selectedResourceListFileEditor.setValue("<No Plan Selected>");

        p.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        p.add(_selectedResourceListFileEditor.getDisplayComponent());
        _distanceUnitCombo.setSelectedUnitSymbol(DistanceUnitRegistry.metres());

        p.add(new GenericNameValuePropertyEditor("inputUnits","Input Units", _distanceUnitCombo, 100 ));

        //@@ todo !!! shouldnt be putting the radius in here!! it is specific to cabbages! so need a resource specific override panel...
        p.add(new GenericNameValuePropertyEditor("radius","Radius", _radiusTextField, 100 ));
        return p;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        _recentTab = createRecentTab();
        _browseTab = createBrowseTab();

        tabbedPane.addTab("Recent", _recentTab);
        tabbedPane.addTab("Browse", _browseTab);
        return tabbedPane;
    }

    private JPanel createButtonPanel() {
        JPanel container = new JPanel(new BorderLayout());

        JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        _openBtn = new JButton("Open");
        _openBtn.setDefaultCapable(true);
        super.getRootPane().setDefaultButton(_openBtn);

        _cancelBtn = new JButton("Cancel");

        p.add(_openBtn);
        p.add(_cancelBtn);

        container.add(p, BorderLayout.NORTH);
        container.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);

        addListeners();


        return container;
    }

    private void addListeners() {

        _recentPlanTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSelectedResourceListFile(_recentPlanTable.getSelectedResourceListFile());
            }


        });
        _browseResourceFilesTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSelectedResourceListFile(_browseResourceFilesTable.getSelectedResourceListFile());
            }


        });
        _openBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPlan();
            }


        });

        _cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelOpen();
            }


        });

        _recentPlanTable.getTable().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPlan();
            }
        });

        _browseResourceFilesTable.getTable().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPlan();
            }
        });
    }

    public ResourceListFile getSelectedResourceListFile() {
        return _selectedResourceListFile;
    }

    public void showOpenPlanDialog() {
        _cancelled = true;
        _selectedResourceListFile = null;
        super.show();
    }
    private void openPlan() {
        if (log.isInfoEnabled() && _selectedResourceListFile != null) {
            log.info("Selected plan: " + _selectedResourceListFile.getName());
        }
        _cancelled = false;
        super.hide();
//        super.dispose();
    }

    private void cancelOpen() {
                       super.hide();
//        super.dispose();
    }


    private JPanel createRecentTab() {
        JPanel tab = new JPanel(new BorderLayout());

        _recentPlanTable = new ResourceListFileTable(true);

        tab.add(_recentPlanTable, BorderLayout.CENTER);
        return tab;
    }

    private JPanel createBrowseTab() {
        JPanel tab = new JPanel(new BorderLayout());

        JPanel p = new JPanel(new BorderLayout());
        _directoryChooser = new FileChooserPropertyEditor(ImportResourceListDialog.class, "directory", "Directory", 100);
        p.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        p.add(_directoryChooser, BorderLayout.NORTH);
        _directoryChooser.initialiseValueFromPreferences();


        tab.add(p, BorderLayout.NORTH);

        _browseResourceFilesTable = new ResourceListFileTable(false);


        tab.add(_browseResourceFilesTable, BorderLayout.CENTER);
        return tab;
    }

    private void setSelectedResourceListFile(ResourceListFile selectedResourceListFile) {
        _selectedResourceListFile = selectedResourceListFile;
        if (selectedResourceListFile != null) {
            _selectedResourceListFileEditor.setValue(selectedResourceListFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        ImportResourceListDialog open = new ImportResourceListDialog((JFrame)null);
//        open.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        open.show();
        System.exit(0);
    }


    public boolean isCancelled() {
        return _cancelled;
    }

    public double getRadius() {
        return Double.parseDouble(_radiusTextField.getText());
    }

    public IDistanceUnit getInputUnits() {
        return _distanceUnitCombo.getSelectedUnit();
    }


    private static final Logger log = Logger.getLogger(ImportResourceListDialog.class);


    private JTabbedPane _tabbedPane;
    private JPanel _recentTab;
    private JPanel _browseTab;
    private JButton _openBtn;
    private JButton _cancelBtn;
    private JPanel _buttonPanel;
    private IPropertyValueEditor _selectedResourceListFileEditor;
    private JPanel _titlePane;
    private ResourceListFileTable _recentPlanTable;

    private ResourceListFile _selectedResourceListFile;
    private ResourceListFileTable _browseResourceFilesTable;
    private FileChooserPropertyEditor _directoryChooser;
    private boolean _cancelled;

    private JTextField _radiusTextField = new JTextField("5.0", 6);
    DistanceUnitComboBox _distanceUnitCombo = new DistanceUnitComboBox(true);
}
