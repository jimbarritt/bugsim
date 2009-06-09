/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.player;

import com.ixcode.bugsim.controller.experiment.IExperiment;
import com.ixcode.bugsim.controller.experiment.IExperimentListener;
import com.ixcode.bugsim.controller.experiment.IExperimentLoop;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlayerFrame extends JFrameExtension implements IExperimentListener, ActionListener {

    public ExperimentPlayerFrame(IExperiment experiment, List loops) throws HeadlessException {
        super("Simulation Player");
        _experiment = experiment;
        _experiment.addExperimentListener(this);
        _loops = loops;


        JPanel content = new JPanel(new BorderLayout());

        content.add(createPropertiesPanel(), BorderLayout.NORTH);

//        IExperimentInspectorPanel inspector = ExperimentInspectorPanelRegistry.getInstance().getPanel(_experiment.getClass());
//        content.add(inspector.getViewComponent(), BorderLayout.CENTER);
//        inspector.setExperiment(_experiment);
        content.add(createControlButtons(), BorderLayout.SOUTH);
        super.getContentPane().add(content);
        super.getStatusBar().setText("");
        setPlayerState(ExperimentPlayerState.PAUSED);

    }

    public void experimentStarted(IExperiment experiment) {
        setPlayerState(ExperimentPlayerState.RUNNING);
        _experimentStatus.setValue("Running ...");


    }

    public void experimentReset(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
        _experimentStatus.setValue("Not Started");
        _experimentStatus.setForeground(Color.blue);
        _currentTimestep.setValue(formatTimestep(_experiment.getSimulation().getExecutedTimesteps()));
        _currentLoopIndexFld.setValue(formatLoopindex(_currentLoopIndex));
        setPlayerState(ExperimentPlayerState.PAUSED);
    }

    public void experimentComplete(IExperiment experiment) {
        setPlayerState(ExperimentPlayerState.COMPLETE);
        _experimentStatus.setValue("Complete");
//        _experimentStatus.setForeground(Color.green);
    }

    public void experimentStopped(IExperiment experiment) {
        setPlayerState(ExperimentPlayerState.PAUSED);
        _stopLoop = true;

//        _experimentStatus.setValue("Stopped");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Output results ?") {
            if (_experiment.hasExperimentReporter()) {
                _experiment.getExperimentReporter().setOutputResults(_outputResults.isSelected());
                updateOutputPathUI();
            }
        }

    }

    private void updateOutputPathUI() {
        if (_experiment.hasExperimentReporter()) {
            _outputResults.setVisible(true);
            if (_experiment.getExperimentReporter().isOutputResults()) {
                _outputPath.setVisible(true);
            } else {
                _outputPath.setVisible(false);
            }
        } else {
            _outputResults.setVisible(false);
            _outputPath.setVisible(false);
        }
    }

    private JPanel createPropertiesPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        container.add(panel, BorderLayout.NORTH);

        EnumerationComboBox combo = createCombo(_loops);
//        combo.setSelectedIndex(0);
        _loopChoiceFld = new EnumerationPropertyEditor("loopSelection", "Loop Strategy", combo, 150);

        _currentLoopIndexFld = new ReadOnlyPropertyEditor("currentLoopIndex", "Current Loop", 150);
        _currentLoopIndexFld.setValue(formatLoopindex(_currentLoopIndex));

        _currentTimestep = new ReadOnlyPropertyEditor("currentTimestep", "Current Timestep", 150);
        _currentTimestep.setValue(formatTimestep(0));

        _currentTimestep.setTextAlignment(TextAlignment.RIGHT);
        _experimentStatus = new ReadOnlyPropertyEditor("experimentComplete", "Experiment Status", 150);
        _experimentStatus.setValue("Not Started");
        _experimentStatus.setTextAlignment(TextAlignment.LEFT);
        _experimentStatus.setForeground(Color.blue);

        _outputPath = new FileChooserPropertyEditor(ExperimentPlayerFrame.class, "outputPath", "Output Path", 150);


        _outputResults = new JCheckBox("Output results ?");
        JPanel cont = new JPanel(new BorderLayout());
        cont.add(_outputResults, BorderLayout.WEST);
        _outputResults.addActionListener(this);
        if (_experiment.hasExperimentReporter()) {
            _outputResults.setSelected(_experiment.getExperimentReporter().isOutputResults());
        }

//        _currentTimestepFld =new JLabel(formatTimestep(0));
//        _currentTimestepFld.setForeground(Color.BLUE);
//        _currentTimestepFld.setHorizontalAlignment(JLabel.RIGHT);
//        currentTimestep.add(new JLabel("Current Timestep:"), BorderLayout.WEST);
//        currentTimestep.add(Box.createHorizontalStrut(300), BorderLayout.WEST);
//        currentTimestep.add(_currentTimestepFld, BorderLayout.CENTER);

        _timestepDelay = new TextFieldPropertyEditor("timestepDelay", "Timestep Delay", "10", 6, TextAlignment.RIGHT, 40);
        _numberOfSteps = new TextFieldPropertyEditor("numberOfSteps", "Number of steps", "1000", 6, TextAlignment.RIGHT, 40);
        _numberOfSteps.setForeground(Color.darkGray);

        panel.add(_loopChoiceFld);
        panel.add(_currentLoopIndexFld);
        panel.add(_currentTimestep);
        panel.add(_experimentStatus);
        panel.add(_numberOfSteps);
        panel.add(_timestepDelay);

        panel.add(cont);
        panel.add(_outputPath);
        updateOutputPathUI();
        return panel;
    }

    private EnumerationComboBox createCombo(List loops) {
        EnumerationComboBox combo = new EnumerationComboBox();
        for (Iterator itr = loops.iterator(); itr.hasNext();) {
            IExperimentLoop loop = (IExperimentLoop)itr.next();
            combo.addValue(loop.getName(), loop.getId());
        }
        return combo;
    }

    private String formatLoopindex(long loopIndex) {
        return "" + (loopIndex + 1);
    }


    private String formatTimestep(long timestep) {
        return FORMAT.format(timestep);
    }

    public void timeStepExecuted(IExperiment experiment, long timestep) {
        _currentTimestep.setValue(formatTimestep(timestep));
    }

    private JPanel createControlButtons() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        _resetBtn = new JButton(new ResetExperimentAction(this));
        _startBtn = new JButton(new RunExperimentAction(_experiment, this));
        _stopBtn = new JButton(new PauseExperimentAction(this));
        _oneStepBtn = new JButton(new SingleStepAction(this));

        JPanel west = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel east = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        west.add(_resetBtn);

        east.add(_oneStepBtn);
        east.add(_startBtn);

        east.add(_stopBtn);

        buttonPanel.add(west, BorderLayout.WEST);
        buttonPanel.add(east, BorderLayout.EAST);

//        _stopBtn.setEnabled(false);
//        _oneStepBtn.setEnabled(false);
//        _resetBtn.setEnabled(false);

        return buttonPanel;
    }

    public long getTimestepsToExecute() {
        return Long.parseLong(_numberOfSteps.getValue());
    }

    public long getTimestepDelay() {
        return Long.parseLong(_timestepDelay.getValue());
    }

    public ExperimentPlayerState getPlayerState() {
        return _playerState;
    }

    public void setPlayerState(ExperimentPlayerState playerState) {
        _playerState = playerState;
        updateUIState(_playerState);
    }

    private void updateUIState(ExperimentPlayerState playerState) {
        if (playerState == ExperimentPlayerState.RUNNING) {
            enableButton(_startBtn, false);
            enableButton(_resetBtn, false);
            enableButton(_stopBtn, true);
            enableButton(_oneStepBtn, false);
            _timestepDelay.setReadonly(true);
            _numberOfSteps.setReadonly(true);
            _outputPath.setReadonly(true);
            _loopChoiceFld.setReadonly(true);
        } else if (playerState == ExperimentPlayerState.PAUSED) {
            enableButton(_resetBtn, true);
            enableButton(_startBtn, true);
            enableButton(_stopBtn, false);
            enableButton(_oneStepBtn, true);
            _timestepDelay.setReadonly(false);
            _numberOfSteps.setReadonly(false);
            _outputPath.setReadonly(false);
            _loopChoiceFld.setReadonly(false);
        } else if (playerState == ExperimentPlayerState.COMPLETE) {
            enableButton(_resetBtn, true);
            enableButton(_startBtn, false);
            enableButton(_stopBtn, false);
            enableButton(_oneStepBtn, false);
            _timestepDelay.setReadonly(false);
            _numberOfSteps.setReadonly(false);
            _outputPath.setReadonly(false);
            _loopChoiceFld.setReadonly(false);
        }
    }

    private void enableButton(JButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setFocusPainted(enabled);
    }

    public IExperiment getExperiment() {
        return _experiment;
    }

    public String getOutputRootPath() {
        return _outputPath.getValue();
    }

    public void updateExperiment() {
        long nextTimestep = _experiment.getSimulation().getExecutedTimesteps() + getTimestepsToExecute() - 1;
        _experiment.setTimestepDelay(getTimestepDelay());
        _experiment.setMaximumTimestep(nextTimestep);
        if (_experiment.hasExperimentReporter()) {
            _experiment.getExperimentReporter().setOutputRootPath(getOutputRootPath());
        }
        setPlayerState(ExperimentPlayerState.RUNNING);
    }

    public void runLoop() {
        IExperimentLoop loop = findLoop(_loops, _loopChoiceFld.getValue());
        _stopLoop = false;
        loop.addLoopListener(_experiment.getExperimentReporter());


        while (!loop.isLoopComplete(_currentLoopIndex, _experiment) && !_stopLoop) {

            if (_experiment.isComplete()) {
                ++_currentLoopIndex;
                loop.initialiseExperimentProperties(_currentLoopIndex, _experiment);
                _experiment.resetExperiment();
            }
            _experiment.run();
            loop.nextIteration(_currentLoopIndex, _experiment);
        }


    }

    private IExperimentLoop findLoop(List loops, String id) {
        IExperimentLoop found = null;
        for (Iterator itr = loops.iterator(); itr.hasNext();) {
            IExperimentLoop loop = (IExperimentLoop)itr.next();
            if (loop.getId().equals(id)) {
                found = loop;
                break;
            }
        }

        return found;

    }


    public void pauseExperiment() {
        _experiment.stopExperiment();
//        _stopLoop = true;
//        setPlayerState(ExperimentPlayerState.PAUSED);

    }

    public void reset() {
        _currentLoopIndex = 0;
        _stopLoop = false;
        IExperimentLoop loop = findLoop(_loops, _loopChoiceFld.getValue());
        loop.initialiseExperimentProperties(_currentLoopIndex, _experiment);
        _experiment.resetExperiment();
        _experiment.resetExperiment();

    }

    public void selectLoop(IExperimentLoop loop) {
        _loopChoiceFld.setValue(loop.getId());
        reset();
    }

    private long _currentLoopIndex = 0;
    private ExperimentPlayerState _playerState = ExperimentPlayerState.PAUSED;

    private JButton _startBtn;
    private JButton _stopBtn;

    private JButton _resetBtn;

    private IExperiment _experiment;

    private NumberFormat FORMAT = new DecimalFormat("0000000");
    private JButton _oneStepBtn;
    private TextFieldPropertyEditor _numberOfSteps;
    private TextFieldPropertyEditor _timestepDelay;
    private ReadOnlyPropertyEditor _currentTimestep;
    private ReadOnlyPropertyEditor _experimentStatus;
    private FileChooserPropertyEditor _outputPath;
    private JCheckBox _outputResults;


    private ReadOnlyPropertyEditor _currentLoopIndexFld;
    private List _loops;
    private EnumerationPropertyEditor _loopChoiceFld;
    private boolean _stopLoop;
}
