/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.framework.experiment.model.ExperimentPlanFile;
import com.ixcode.framework.experiment.model.xml.ExperimentPlanXML;
import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.FileChooserPropertyEditor;
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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class OpenExperimentPlanDialog extends JDialog {

    public OpenExperimentPlanDialog() throws HeadlessException {
        super();

        init();

    }

    public OpenExperimentPlanDialog(Frame owner) throws HeadlessException {
        super(owner);
        init();
    }

    public OpenExperimentPlanDialog(JDialog owner) throws HeadlessException {
        super(owner);

        init();

    }

    private void init() {
        super.setModal(true);


        super.setTitle("Open Experiment Plan ...");

        initUI();

//        initRecentPlans();
        updateBrowseFiles();

        if (_tabbedPane.getTabCount()>1) {
            _tabbedPane.setSelectedIndex(1);
        }

        super.setSize(800, 600);
        JFrameExtension.centreWindowOnScreen(this);
    }

    private void updateBrowseFiles() {
        try {
            List newPlans;
            if (_directoryChooser.getValue() != null && _directoryChooser.getValue().length() > 0) {
                newPlans = ExperimentPlanXML.INSTANCE.readPlanFiles(new File(_directoryChooser.getValue()));
            } else {
                newPlans = new ArrayList();
            }
            _browsePlanTable.setPlanFiles(newPlans);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initRecentPlans() {
        List recentPlans = new ArrayList();
        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrX.xml", "Test Sensory Perception Experiment"));
        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrA.xml", "Blah blah bla"));
        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrB.xml", "Some more nonsens"));
        recentPlans.add(new ExperimentPlanFile("/someDirectory/exp-plan-expX-TrC.xml", "And some more!!"));

        _recentPlanTable.setPlanFiles(recentPlans);
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
        JPanel p = new JPanel(new BorderLayout());
        _selectedPlanEditor = new ReadOnlyPropertyEditor("currenPlan", "Selected Plan", 100);
        _selectedPlanEditor.setValue("<No Plan Selected>");

        p.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        p.add(_selectedPlanEditor.getDisplayComponent(), BorderLayout.NORTH);
        return p;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        _recentTab = createRecentTab();
        _browseTab = createBrowseTab();

//        tabbedPane.addTab("Recent", _recentTab);
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
                setSelectedPlan(_recentPlanTable.getSelectedPlan());
            }


        });
        _browsePlanTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSelectedPlan(_browsePlanTable.getSelectedPlan());
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

        _browsePlanTable.getTable().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPlan();
            }
        });
    }


    public void showOpenPlanDialog() {
        _cancelled = true;
        _selectedPlan = null;
        super.show();
    }
    private void openPlan() {
        if (log.isInfoEnabled() && _selectedPlan != null) {
            log.info("Selected plan: " + _selectedPlan.getName());
        }
        _cancelled = false;
        super.hide();
//        super.dispose();
    }

    private void cancelOpen() {
                       super.hide();
//        super.dispose();
    }

    public ExperimentPlanFile getSelectedPlanFile() {
        return _selectedPlan;
    }

    private JPanel createRecentTab() {
        JPanel tab = new JPanel(new BorderLayout());

        _recentPlanTable = new ExperimentPlanTable(true);

        tab.add(_recentPlanTable, BorderLayout.CENTER);
        return tab;
    }

    private JPanel createBrowseTab() {
        JPanel tab = new JPanel(new BorderLayout());

        JPanel p = new JPanel(new BorderLayout());
        _directoryChooser = new FileChooserPropertyEditor(OpenExperimentPlanDialog.class, "directory", "Directory", 100);
        p.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        p.add(_directoryChooser, BorderLayout.NORTH);
        _directoryChooser.initialiseValueFromPreferences();

        _directoryChooser.getFileChooserTextField().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                updateBrowseFiles();
            }
        });

        tab.add(p, BorderLayout.NORTH);

        _browsePlanTable = new ExperimentPlanTable(false);

        JTabbedPane listOfPlans = new JTabbedPane();
        listOfPlans.addTab("List of Experiment Plans", _browsePlanTable);

        tab.add(listOfPlans, BorderLayout.CENTER);
        return tab;
    }

    private void setSelectedPlan(ExperimentPlanFile selectedPlan) {
        _selectedPlan = selectedPlan;
        _selectedPlanEditor.setValue(selectedPlan.getAbsolutePath());
    }

    public static void main(String[] args) {
        OpenExperimentPlanDialog open = new OpenExperimentPlanDialog((JFrame)null);
//        open.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        open.show();
        System.exit(0);
    }


    public boolean isCancelled() {
        return _cancelled;
    }

    private JTabbedPane _tabbedPane;
    private JPanel _recentTab;
    private JPanel _browseTab;
    private JButton _openBtn;
    private JButton _cancelBtn;
    private JPanel _buttonPanel;
    private IPropertyValueEditor _selectedPlanEditor;
    private JPanel _titlePane;
    private ExperimentPlanTable _recentPlanTable;
    private static final Logger log = Logger.getLogger(OpenExperimentPlanDialog.class);
    private ExperimentPlanFile _selectedPlan;
    private ExperimentPlanTable _browsePlanTable;
    private FileChooserPropertyEditor _directoryChooser;
    private boolean _cancelled;
}
