/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.ExperimentState;
import com.ixcode.framework.experiment.model.IExperimentProgressListener;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.*;
import com.ixcode.framework.util.MemoryStats;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.view.tree.ParameterSelectionDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Description : Shows us the state of the experiment controller
 */
public class ExperimentControllerPanel extends JPanel implements PropertyChangeListener, IExperimentProgressListener {

    public ExperimentControllerPanel(ExperimentController controller, JComponent experimentProgressPanel) throws JavaBeanException {
        super.setLayout(new BorderLayout());
        initUI(controller, experimentProgressPanel);

        _controller = controller;

    }

    private void initUI(ExperimentController controller, JComponent experimentProgressPanel) throws JavaBeanException {
        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.PAGE_AXIS));


        JPanel progressPanel = createProgressPanel(controller);
        layoutPanel.add(progressPanel);

        layoutPanel.add(experimentProgressPanel);

        JPanel propertiesPanel = createPropertiesPanel(controller);
        layoutPanel.add(propertiesPanel);

//        JPanel memoryPanel = createMemoryPanel(controller);
//        layoutPanel.add(memoryPanel);

        super.add(layoutPanel, BorderLayout.NORTH);

        controller.addPropertyChangeListener(ExperimentController.PROPERTY_STATE, this);
        controller.addProgressListener(this, 1);
        updateUIState(ExperimentState.READY);
    }


    public void propertyChange(PropertyChangeEvent evt) {
        ExperimentState state = (ExperimentState)evt.getNewValue();
        updateUIState(state);

        _experimentStatusFld.setValue(state.getName());
    }

    public void progressNotification(ExperimentProgress progress) {
        _currentIterationFld.setValue(progress.getCurrentIterationFormatted());
        _currentReplicantFld.setValue(progress.getCurrentReplicateFormatted());
        _currentTimestepFld.setValue(progress.getIterationTimestepsExecuted());
        _elapsedTimeFld.setValue(progress.getElapsedTimeFormatted());
//        _memStatsFld.setValue(_memStats.getFreeMb() + " MB out of " + _memStats.getJvmMb() + " MB (Max" + _memStats.getMaxMb() + " MB)");
    }


    private JPanel createMemoryPanel(ExperimentController controller) {
        PropertyGroupPanel memoryPanel = new PropertyGroupPanel("Memory Usage");
        _memStatsFld = new ReadOnlyPropertyEditor("memStats", "In Use ", 150);
        memoryPanel.addPropertyEditor(_memStatsFld);
        return memoryPanel;
    }

    private JPanel createProgressPanel(ExperimentController controller) throws JavaBeanException {

//        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.PAGE_AXIS));




        _currentIterationFld = new ReadOnlyPropertyEditor(ExperimentProgress.PROPERTY_CURRENT_ITERATION, "Current Iteration", 150);
        _currentIterationFld.setValue(formatIterationNumber(0));

        _currentReplicantFld = new ReadOnlyPropertyEditor(ExperimentProgress.PROPERTY_CURRENT_REPLICANT, "Current Replicant", 150);
        _currentReplicantFld.setValue(formatIterationNumber(0));


        _currentTimestepFld = new ReadOnlyPropertyEditor(ExperimentProgress.PROPERTY_CURRENT_ITERATION_TIMESTEP, "Current Timestep", 150);
        _currentTimestepFld.setValue(formatTimestep(0));
        _currentTimestepFld.setTextAlignment(TextAlignment.RIGHT);

        _elapsedTimeFld = new ReadOnlyPropertyEditor(ExperimentProgress.PROPERTY_ELAPSED_TIME, "Elapsed Time", 150);
        _elapsedTimeFld.setValue("00:00:00");

        _experimentStatusFld = new ReadOnlyPropertyEditor(ExperimentController.PROPERTY_STATE, "Experiment Status", 150);
        _experimentStatusFld.setValue(ExperimentState.READY.getName());
        _experimentStatusFld.setTextAlignment(TextAlignment.LEFT);
        _experimentStatusFld.setForeground(Color.blue);


        _experimentOutputPathFld = new ReadOnlyPropertyEditor(ExperimentController.PROPERTY_EXPERIMENT_OUTPUT_DIR, "Experiment Output Dir", 150);

        JavaBeanModelAdapter modelAdapter = new JavaBeanModelAdapter();
        modelAdapter.getFormatter().registerFormat(Locale.UK, File.class,  new IJavaBeanValueFormat(){
            public String format(Object value) {
                return ((File)value).getName();
            }

            public Object parse(String value) throws JavaBeanParseException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        PropertyBinding.bindEditor(_experimentOutputPathFld, modelAdapter, controller);


        JPanel showParametersPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        _btnShowParams =new JButton("Show Parameters");
        _btnShowParams.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                showParameters();
            }
        });
        showParametersPanel.add(_btnShowParams);

        PropertyGroupPanel progressPanel = new PropertyGroupPanel("Progress");
        progressPanel.addPropertyEditor(_experimentStatusFld);
        progressPanel.addPropertyEditor(_experimentOutputPathFld);
        progressPanel.addPropertyEditor(_currentIterationFld);
        progressPanel.addPropertyEditor(_currentReplicantFld);
        progressPanel.addPropertyEditor(_currentTimestepFld);
        progressPanel.addPropertyEditor(_elapsedTimeFld);
        progressPanel.getLayoutPanel().add(showParametersPanel);

        return progressPanel;


    }

    public void showParameters() {
        ParameterMap params = _controller.getCurrentParameterMap();
        ParameterSelectionDialog _parameterSelect = new ParameterSelectionDialog((JFrame)this.getRootPane().getParent(), params);
        _parameterSelect.setModal(false);
        _parameterSelect.setSelectVisible(false);
        _parameterSelect.selectParameter();
    }

    private JPanel createPropertiesPanel(ExperimentController controller) throws JavaBeanException {
        _outputPathFld = new FileChooserPropertyEditor(ExperimentController.class, ExperimentController.PROPERTY_OUTPUT_ROOT_PATH, "Output Path", 150);

        _outputResultsFld = new CheckBoxPropertyEditor(ExperimentController.PROPERTY_OUTPUT_RESULTS, "Output results ?");
        _outputResultsFld.setValue("true");

        _outputIterationDetailsFld = new CheckBoxPropertyEditor(ExperimentController.PROPERTY_OUTPUT_ITERATION_DETAILS, "Output iteration details ?");


        _timestepDelayFld = new TextFieldPropertyEditor(ExperimentController.PROPERTY_TIMESTEP_DELAY, "Timestep Delay", "" + controller.getTimestepDelay(), 6, TextAlignment.RIGHT, 30);

        _numberOfStepsFld = new TextFieldPropertyEditor(ExperimentController.PROPERTY_MAX_TIMESTEPS, "Number of steps", "" + controller.getMaxStepsToExecute(), 6, TextAlignment.RIGHT, 30);
        _numberOfStepsFld.setForeground(Color.darkGray);


        PropertyGroupPanel propertiesPanel = new PropertyGroupPanel("Properties");
        propertiesPanel.addPropertyEditor(_outputResultsFld);
        propertiesPanel.addPropertyEditor(_outputPathFld);
        propertiesPanel.addPropertyEditor(_outputIterationDetailsFld);
        propertiesPanel.addPropertyEditor(_numberOfStepsFld);
        propertiesPanel.addPropertyEditor(_timestepDelayFld);

        IModelAdapter modelAdapter = new JavaBeanModelAdapter();
        PropertyBinding.bindEditor(_outputResultsFld, modelAdapter, controller);
        PropertyBinding.bindEditor(_outputPathFld, modelAdapter, controller);
        PropertyBinding.bindEditor(_outputIterationDetailsFld, modelAdapter, controller);
        PropertyBinding.bindEditor(_numberOfStepsFld, modelAdapter, controller);
        PropertyBinding.bindEditor(_timestepDelayFld, modelAdapter, controller);

      

        return propertiesPanel;

    }


    private void updateUIState(ExperimentState state) {

        if (state == ExperimentState.READY || state == ExperimentState.PAUSED) {
            _outputPathFld.setReadonly(false);
            _outputResultsFld.setReadonly(false);
            _outputIterationDetailsFld.setReadonly(false);
            _timestepDelayFld.setReadonly(false);
            _numberOfStepsFld.setReadonly(false);
            _btnShowParams.setEnabled(true);
        } else {
            _outputPathFld.setReadonly(true);
            _outputResultsFld.setReadonly(true);
            _outputIterationDetailsFld.setReadonly(true);
            _timestepDelayFld.setReadonly(true);
            _numberOfStepsFld.setReadonly(true);
            _btnShowParams.setEnabled(false);
        }

        if (state == ExperimentState.COMPLETE) {
            _experimentStatusFld.setForeground(Color.GREEN);
        } else {
            _experimentStatusFld.setForeground(Color.blue);
        }
    }

    private String formatIterationNumber(long loopIndex) {
        return "" + (loopIndex + 1);
    }


    private String formatTimestep(long timestep) {
        return FORMAT.format(timestep);
    }

    private NumberFormat FORMAT = new DecimalFormat("0000000");

    private ReadOnlyPropertyEditor _experimentStatusFld;
    private ReadOnlyPropertyEditor _currentIterationFld;
    private ReadOnlyPropertyEditor _currentTimestepFld;
    private ReadOnlyPropertyEditor _elapsedTimeFld;

    private FileChooserPropertyEditor _outputPathFld;
    private CheckBoxPropertyEditor _outputResultsFld;
    private TextFieldPropertyEditor _timestepDelayFld;
    private TextFieldPropertyEditor _numberOfStepsFld;

    private ReadOnlyPropertyEditor _currentReplicantFld;
    private ReadOnlyPropertyEditor _experimentOutputPathFld;
    private ReadOnlyPropertyEditor _memStatsFld;
    private MemoryStats _memStats = new MemoryStats(Runtime.getRuntime());
    private CheckBoxPropertyEditor _outputIterationDetailsFld;
    private JButton _btnShowParams
            ;
    private ExperimentController _controller;
}
