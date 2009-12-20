/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.bugsim.experiment.BugsimExperimentTemplateRegistry;
import com.ixcode.bugsim.experiment.BugsimExtensionJavaBeanValueFormats;
import com.ixcode.bugsim.experiment.experimentX.ExperimentXFactory;
import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.bugsim.view.experiment.editor.forager.ForagerCategoryPanel;
import com.ixcode.bugsim.view.experiment.editor.landscape.LandscapeCategoryPanel;
import com.ixcode.bugsim.view.experiment.editor.manipulation.ManipulationPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.ResourceCategoryPanel;
import com.ixcode.bugsim.view.experiment.editor.Simulation.SimulationCategoryPanel;
import com.ixcode.bugsim.BugsimMain;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentPlanFile;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.xml.ExperimentPlanXML;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.ExperimentPlanTemplate;
import com.ixcode.framework.simulation.experiment.ExperimentTemplateRegistry;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Description : Allows creating / Editing of experiment setup.
 */
public class ExperimentPlanEditorDialog extends JFrame implements PropertyChangeListener , IParameterMapLookup {

    /**
     * @param online wether we are editing files or in memory
     */
    public ExperimentPlanEditorDialog(boolean online) {
        super();
        _online = online;
        initUI();
        initFromPreferences();
        ParameterMapXML.initModelAdapter(_modelAdapter);

    }

    public void setTitle(String filename) {
        super.setTitle("Experiment Plan Editor [" + filename + "]");
    }

    public void initFromPreferences() {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        _outputDir = (String)prefs.get(PREF_OUTPUT_DIR, _outputDir);

        initWindowCloseListener();
    }


    private void initUI() {

        JPanel container = new JPanel(new BorderLayout());

        container.add(createTitlePanel(), BorderLayout.NORTH);

        _categoryTabs = createTabbedPanel();
        container.add(_categoryTabs, BorderLayout.CENTER);

        container.add(createButtonPanel(), BorderLayout.SOUTH);

        super.getContentPane().add(container);


    }

    private void initWindowCloseListener() {
//        super.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                try {
//                    if (!confirmSave()) {
//                        //@todo think can do it by changing the default close operation ....
//                        throw new IllegalStateException("Need to stop the window closing somehow!!");
//                    }
//                } catch (IOException e1) {
//                    throw new RuntimeException(e1);
//                }
//            }
//        });
    }

    private JPanel createTitlePanel() {
        _titlePanel = new PropertyGroupPanel();

        _filenameEditor = new ReadOnlyPropertyEditor("filename", "File", 100, _btnOpen);


        if (_online) {
            _filename = "Online";
            setTitle("online");
        } else {
            setTitle("untitled.xml");
        }
        _filenameEditor.setValue(_filename);

        _titlePanel.addPropertyEditor(_filenameEditor);

//        _titlePanel.addPropertyChangeListener(this);
        return _titlePanel;

    }

    private JPanel addGeneralTab(JTabbedPane tabbedPane, IModelAdapter modelAdapter) {
        _generalTab = new ParameterGroupPanel(modelAdapter);


        _templateNameEditor = createTemplateNameEditor();
        _generalTab .getLayoutPanel().add(_templateNameEditor);
        int minWidth = 120;
        IPropertyValueEditor nameE = _generalTab .addPropertyEditor(ExperimentPlan.P_NAME, "Name", 15, TextAlignment.LEFT, minWidth);
        IPropertyValueEditor descriptionE = _generalTab .addPropertyEditor(ExperimentPlan.P_DESCRIPTION, "Description", 50, TextAlignment.LEFT, minWidth);
        IPropertyValueEditor trialNameE = _generalTab .addPropertyEditor(ExperimentPlan.P_TRIAL_NAME, "Trial Name", 15, TextAlignment.LEFT, minWidth);
        IPropertyValueEditor experimentNameE = _generalTab .addPropertyEditor(ExperimentPlan.P_EXPERIMENT_NAME, "Experiment Name", 15, TextAlignment.LEFT, minWidth);
        IPropertyValueEditor outputDirName= _generalTab .addPropertyEditor(ExperimentPlan.P_OUTPUT_DIR_NAME, "Output Dir Name", 15, TextAlignment.LEFT, minWidth);


        _generalTab.addPropertyEditorBinding(nameE);
        _generalTab.addPropertyEditorBinding(descriptionE);
        _generalTab.addPropertyEditorBinding(trialNameE);
        _generalTab.addPropertyEditorBinding(experimentNameE);
        _generalTab.addPropertyEditorBinding(outputDirName);

        tabbedPane.add(TAB_GENERAL, _generalTab);
        return _generalTab;
    }


    private EnumerationPropertyEditor createTemplateNameEditor() {

        _templateNameCombo = new EnumerationComboBox();
        List templates = ExperimentTemplateRegistry.getInstance().getTemplates();

        for (Iterator itr = templates.iterator(); itr.hasNext();) {
            ExperimentPlanTemplate experimentPlanTemplate = (ExperimentPlanTemplate)itr.next();
            _templateNameCombo.addValue(experimentPlanTemplate.getShortDescription(), experimentPlanTemplate.getTemplateName());
        }
        IEnumerationDescriptionLookup lookup = new IEnumerationDescriptionLookup() {
            public String getDescriptionForValue(String value) {
                return ExperimentTemplateRegistry.getInstance().getTemplate(value).getLongDescription();
            }
        };
        EnumerationPropertyEditor editor = new EnumerationPropertyEditor("templateName", "Template", _templateNameCombo, 100, lookup);
        _browseTemplateBtn = new JButton("Browse ...");
        //@todo implement browseing - so you can see a list of templates (maybe even organised by grouping?) with their descriptions.
        _browseTemplateBtn.setEnabled(false);
        editor.addExtraComponent(_browseTemplateBtn);

        _templateNameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!_loading) {
                    updatePlanFromTemplate();
                }
            }


        });
        return editor;

    }

    private JTabbedPane createTabbedPanel() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        addGeneralTab(tabbedPane, _modelAdapter);
        addSimulationTab(tabbedPane);
        addLandscapeTab(tabbedPane);
        addResourceTab(tabbedPane);
        addForagerTab(tabbedPane, _modelAdapter);
        addManipulationsTab(tabbedPane);
        return tabbedPane;
    }

    private void addManipulationsTab(JTabbedPane tabbedPane) {
        _manipulationPanel = new ManipulationPanel(_modelAdapter, this);
        tabbedPane.addTab(TAB_MANIPULATION, _manipulationPanel);
    }

    private JPanel createButtonPanel() {
        JPanel btnContainer = new JPanel(new BorderLayout());

        btnContainer.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        if (_online) {
            buttonPanel.add(_btnSaveAs);
            buttonPanel.add(_btnOk);
            buttonPanel.add(_btnCancel);
        } else {
            buttonPanel.add(_btnSave);
            buttonPanel.add(_btnSaveAs);
            buttonPanel.add(_btnClose);
        }

        _btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (confirmSave()) {
                        openPlan();
                    }
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            }
        });

        _btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (confirmSave()) {
                        closeEditor();
                    }
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            }
        });

        _btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    savePlan();
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            }
        });

        _btnSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAsPlan();
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            }
        });

        _btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _cancelled = false;
//                updatePlan();
                closeEditor();

            }
        });
        _btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _cancelled = true;
                closeEditor();

            }
        });
        btnContainer.add(buttonPanel, BorderLayout.NORTH);
        return btnContainer;
    }

    private void closeEditor() {
        super.hide();
        super.dispose();
    }

    private void syncPrefs(String filePath) {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        try {
            prefs.put(PREF_OUTPUT_DIR, filePath);
            prefs.sync();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }


    private boolean confirmSave() throws IOException {
        boolean continueAction = true;
        if (_dirty) {
            int result = JOptionPane.showConfirmDialog(this, "Do you want to save changes to plan '" + _plan.getName() + "' ?", "Unsaved changes...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                continueAction = savePlan();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                continueAction = false;
            }
        }
        return continueAction;
    }

    private boolean savePlan() throws IOException {
        boolean saved = true;
        if (_filename == null) {
            saved = saveAsPlan();
        } else {
            savePlan(new File(_outputDir, _filename));
        }

        if (saved && _dirty) {
            setClean();
        }
        return saved;
    }

    private boolean saveAsPlan() throws IOException {
        boolean saved = true;
        updateTitle();
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Save plan '" + _plan.getName() + " as ...");
        fileChooser.setSelectedFile(new File(createPlanFilename(_plan)));
        fileChooser.setCurrentDirectory(new File(_outputDir));
        fileChooser.setFileFilter(PLAN_FILE_FILTER);
        int result = fileChooser.showSaveDialog(this);

        if ((result == JFileChooser.APPROVE_OPTION) && fileChooser.getSelectedFile() != null) {
            File file = fileChooser.getSelectedFile();
            savePlan(file);
            _outputDir = file.getParentFile().getAbsolutePath();

        } else {
            saved = false;
        }


        syncPrefs(_outputDir);


        if (saved) {
            setClean();
        }
        return saved;
    }


    private void openPlan() {
        try {
            this.setEnabled(false);
            OpenExperimentPlanDialog openD = new OpenExperimentPlanDialog(this);

            openD.showOpenPlanDialog();

            if (!openD.isCancelled()) {
                ExperimentPlanFile planFile = openD.getSelectedPlanFile();
                ExperimentPlan plan = ExperimentPlanXML.INSTANCE.importPlan(planFile);
                setFileName(planFile.getName());
                _outputDir = planFile.getParentFile().getAbsolutePath();
                setPlan(plan);
            }
            this.setEnabled(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String createPlanFilename(ExperimentPlan plan) {
        return "exp-plan-" + plan.getName() + "-" + plan.getTrialName() + ".xml";
    }

    private void savePlan(File file) throws IOException {
        updatePlan();
        if (log.isInfoEnabled()) {
            log.info("Saving plan to file : " + file.getAbsolutePath());
        }
        ExperimentPlanXML.INSTANCE.exportPlan(file, _plan, true);
        setFileName(file.getName());
    }

    private void setFileName(String name) {
        _filename = name;
        _filenameEditor.setValue(_outputDir + File.pathSeparator + name);
        setTitle(_filename);
    }


    private void addForagerTab(JTabbedPane tabbedPane, IModelAdapter modelAdapter) {
        _foragerCategoryPanel = new ForagerCategoryPanel(modelAdapter);
        tabbedPane.addTab(TAB_FORAGER, _foragerCategoryPanel);
    }

    private void addResourceTab(JTabbedPane tabbedPane) {
        _resourceCategoryPanel = new ResourceCategoryPanel(_modelAdapter);

        tabbedPane.addTab(TAB_RESOURCE, _resourceCategoryPanel);
    }

    private void addLandscapeTab(JTabbedPane tabbedPane) {
        _landscapeCategoryPanel = new LandscapeCategoryPanel(_modelAdapter);
//        _landscapePanel = _landscapeTab.getLandscapeBoundaryPanel();
        tabbedPane.addTab(TAB_LANDSCAPE, _landscapeCategoryPanel);
    }

    private void addSimulationTab(JTabbedPane tabbedPane) {
        _simulationPanel = new SimulationCategoryPanel(_modelAdapter);
        tabbedPane.addTab(TAB_SIMULATION, _simulationPanel);
    }

    /**
     * Online means that
     *
     * @param plan
     * @param modal
     */
    public void editPlan(ExperimentPlan plan, boolean modal) {
        super.setSize(800, 800);
        JFrameExtension.centreWindowOnScreen(this);
        setPlan(plan);
//        super.setModal(modal);
        _cancelled = true;
        super.setVisible(true);


    }

    public ParameterMap getParameterMap() {
        return (_plan != null) ? _plan.getParameterTemplate() : null;
    }

    private void setPlan(ExperimentPlan plan) {
        _loading = true;
        _plan = plan;
        if (_plan.getTemplateName() != null) {
            _templateNameEditor.setValue(_plan.getTemplateName());
        }


        BugsimParameterMap bugsimParams = new BugsimParameterMap(_plan.getParameterTemplate(), true);

//        debugResourceBounds(bugsimParams);

//        List editors = _titlePanel.getPropertyValueEditors();
//        for (Iterator itr = editors.iterator(); itr.hasNext();) {
//            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
//            String name = editor.getPropertyName();
//            if (!editor.isDisplayOnly()) {
//                editor.setValue(_modelAdapter.getPropertyValueAsString(_plan, name, _locale));
//            }
//        }

        SimulationCategory simC = bugsimParams.getSimulationCategory();

        _generalTab.setModel(_plan);
        _landscapeCategoryPanel.setBugsimParameters(bugsimParams);
        _resourceCategoryPanel.setResourceCategory(bugsimParams.getResourceCategory());
        _foragerCategoryPanel.setForagerCategory(bugsimParams.getForagerCategory());
        _manipulationPanel.setParameterManipulations(new ParameterManipulations(plan, plan.getParameterTemplate(), plan.getParameterManipulationSequences()));
        _simulationPanel.setSimulationCategory(bugsimParams.getSimulationCategory());

        _loading = false;
        setClean();

//        testBoundaryEvents(bugsimParams);
    }

    private void testBoundaryEvents(BugsimParameterMap bugsimParams) {
        Parameter outerBoundsP = bugsimParams.getLandscapeCategory().getExtent().getOuterBoundary().getBoundsP();


        Parameter locationDP = bugsimParams.getResourceCategory().getResourceLayout().getLayoutBoundary().getLocationP();
        locationDP.addParameterListener(new IParameterListener() {

            public void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue) {

            }

            public void parameterConnected(ParameterEvent evt) {

            }

            public void parameterDisconnected(ParameterDisconnectedEvent event) {

            }

            public void parameterValueChanged(ParameterValueChangedEvent event) {
                if (log.isInfoEnabled()) {
                    log.info("!!!!! SHOULD RECIEVED UPDATE VENT!!!! " + event);
                }

            }
        });

        RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)bugsimParams.getResourceCategory().getResourceLayout().getLayoutBoundary();
        rbs.addPropertyChangeListener("location", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (log.isInfoEnabled()) {
                    log.info("PROPERTYCHANGED!!!!!! " + evt.getPropertyName() + " : " + evt.getNewValue());
                }
            }
        });

        outerBoundsP.fireParameterValueChangedEvent("TEST_OLD", "TEST", new Stack());


        BoundaryStrategyBase boundary = bugsimParams.getResourceCategory().getResourceLayout().getLayoutBoundary();
        if (boundary instanceof RectangularBoundaryStrategy) {
            Parameter dimensionsP = ((RectangularBoundaryStrategy)boundary).getDimensionsP();
            dimensionsP.fireParameterValueChangedEvent("TEST_OLD", "TEST", new Stack());
            ((RectangularBoundaryStrategy)boundary).setDimensions(new CartesianDimensions(600));
        }

        if (log.isInfoEnabled()) {
            log.info("SHould have fired all these events!");
        }
    }

    private void debugResourceBounds(BugsimParameterMap bugsimParameters) {
        if (log.isInfoEnabled()) {
            if (bugsimParameters.getLandscapeCategory().getExtent() instanceof DistancedExtentStrategy) {
                DerivedParameter innerBoundaryDP = (DerivedParameter)((DistancedExtentStrategy)bugsimParameters.getLandscapeCategory().getExtent()).getInnerBoundaryP();
                Parameter innerBoundarySourceP = innerBoundaryDP.getSourceParameters().getParameter(ResourceLayoutStrategyBase.P_LAYOUT_BOUNDARY);
                log.info("InnerBoundarySourceP : " + IntrospectionUtils.getObjectId(innerBoundarySourceP) + " : " + innerBoundarySourceP.getFullyQualifiedName());
                Parameter fromDerived = innerBoundaryDP.getStrategyDefinitionValue();
                log.info("FromDerived          : " + IntrospectionUtils.getObjectId(fromDerived) + " : " + fromDerived.getFullyQualifiedName());
                Parameter resourceBoundaryP = bugsimParameters.getResourceCategory().getResourceLayout().getLayoutBoundaryP();
                log.info("ResourceBoundaryP    : " + IntrospectionUtils.getObjectId(resourceBoundaryP) + " : " + resourceBoundaryP.getFullyQualifiedName());
                Parameter resourceBoundaryS = resourceBoundaryP.getStrategyDefinitionValue();
                log.info("ResourceBoundaryS    : " + IntrospectionUtils.getObjectId(resourceBoundaryS) + " : " + resourceBoundaryS.getFullyQualifiedName());

                log.info("Are they the same? : " + (innerBoundaryDP == resourceBoundaryP));

                log.info("InnerBoundaryStrategy    : " + IntrospectionUtils.getObjectId(((DistancedExtentStrategy)bugsimParameters.getLandscapeCategory().getExtent()).getInnerBoundary()));
                log.info("ResourceBoundaryStrategy : " + IntrospectionUtils.getObjectId(bugsimParameters.getResourceCategory().getResourceLayout().getLayoutBoundary()));

            }
        }
    }

    private void updatePlanFromTemplate() {
        ExperimentPlanTemplate planTemplate = ExperimentTemplateRegistry.getInstance().getTemplate(_templateNameCombo.getSelectedValue());
        if (log.isInfoEnabled()) {
            log.info("Changing plan to new template: " + planTemplate.getTemplateName());
        }
        ExperimentPlan plan = planTemplate.createPlan();
        setPlan(plan);
    }


    private void updatePlan() {
        updateTitle();
        

    }


    private void updateTitle() {
//        List editors = _titlePanel.getPropertyValueEditors();
//        for (Iterator itr = editors.iterator(); itr.hasNext();) {
//            IPropertyValueEditor editor = (IPropertyValueEditor)itr.next();
//            String name = editor.getPropertyName();
//            if (!editor.isDisplayOnly()) {
//                _modelAdapter.setPropertyValueAsString(_plan, name, editor.getValue(), Locale.UK);
//            }
//        }
    }


    public static void main(String[] args) {
//        ExperimentPlan testPlan = new ExperimentPlan("newPlan");

        if (log.isInfoEnabled()) {
            log.info("Welcome To The BugSim Experimental planeditor version " + BugsimMain.getVersion());
        }
        JFrameExtension.setSystemLookAndFeel();
        BugsimExtensionJavaBeanValueFormats.registerBugsimJavaBeanExtensionFormats();
        ExperimentTemplateRegistry.setExperimentTemplateInstance(new BugsimExperimentTemplateRegistry());
        IExperiment experiment = (new ExperimentXFactory()).createExperiment("TrX");
        ExperimentPlan testPlan = experiment.getExperimentPlan();




        ExperimentPlanEditorDialog f = new ExperimentPlanEditorDialog(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setTab(ExperimentPlanEditorDialog.TAB_MANIPULATION);

        f.editPlan(testPlan, false);


//        System.exit(0);

    }

    private void setTab(String name) {
        int index = 0;
        for (int i = 0; i < _categoryTabs.getTabCount(); ++i) {
            if (_categoryTabs.getTitleAt(i).equals(name)) {
                index = i;
                break;
            }
        }
        _categoryTabs.setSelectedIndex(index);
    }

    public void propertyChange(PropertyChangeEvent evt) {

        if (!_loading && evt.getPropertyName().startsWith(IPropertyValueEditor.EDITED_PROPERTY)) {
            if (log.isDebugEnabled()) {
                log.debug("PropertyChanged (" + evt.getSource().getClass().getName() + ") : " + evt.getPropertyName());
            }
            setDirty();
        }
    }

    private void setDirty() {
        _dirty = true;
        enableSaveButtons(true);

    }

    private void setClean() {
        _dirty = false;
        enableSaveButtons(false);

    }

    private void enableSaveButtons(boolean enabled) {
        _btnSave.setEnabled(enabled);
//        _btnSaveAs.setEnabled(enabled);
    }

    public ExperimentPlan getPlan() {
        return _plan;
    }


    private static final Logger log = Logger.getLogger(ExperimentPlanEditorDialog.class);
    private JavaBeanModelAdapter _modelAdapter = new JavaBeanModelAdapter();

    private Locale _locale = Locale.UK;
    private ExperimentPlan _plan;
    private PropertyGroupPanel _titlePanel;

    private JButton _btnOk = new JButton("Ok");
    private JButton _btnCancel = new JButton("Cancel");

    private JButton _btnOpen = new JButton("Open ...");
    private JButton _btnSave = new JButton("Save");
    private JButton _btnSaveAs = new JButton("Save As...");
    private JButton _btnClose = new JButton("Close");
    private boolean _dirty = true;
    private String _filename;
    private String _outputDir = ".";
    private static final FileFilter PLAN_FILE_FILTER = new FileFilter() {
        public boolean accept(File f) {
            return f.getName().endsWith(".xml");
        }

        public String getDescription() {
            return "Experiment Plan (*.xml)";
        }
    };

    public boolean isCancelled() {
        return _cancelled;
    }

    private static final String PREF_OUTPUT_DIR = "outputDir";
    private SimulationCategoryPanel _simulationPanel;

    private PropertyGroupPanel _landscapePanel;
    private boolean _loading;
    private ReadOnlyPropertyEditor _filenameEditor;
    private LandscapeCategoryPanel _landscapeCategoryPanel;
    private JTabbedPane _categoryTabs;
    public static final String TAB_LANDSCAPE = "Landscape";
    public static final String TAB_RESOURCE = "Resources";
    public static final String TAB_FORAGER = "Forager";
    public static final String TAB_SIMULATION = "Simulation";
    public static final String TAB_MANIPULATION = "Manipulations";
    private boolean _online;
    private boolean _cancelled;
    private EnumerationComboBox _templateNameCombo;
    private EnumerationPropertyEditor _templateNameEditor;
    private JButton _browseTemplateBtn;
    private ResourceCategoryPanel _resourceCategoryPanel;
    private ForagerCategoryPanel _foragerCategoryPanel;
    private ParameterGroupPanel _generalTab;
    private static final String TAB_GENERAL = "General";
    private ManipulationPanel _manipulationPanel;
}
