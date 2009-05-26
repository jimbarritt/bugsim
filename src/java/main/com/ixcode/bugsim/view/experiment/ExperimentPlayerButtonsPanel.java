/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.framework.experiment.model.ExperimentController;

import com.ixcode.framework.experiment.model.ExperimentState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Description :
 */
public class ExperimentPlayerButtonsPanel extends JPanel implements PropertyChangeListener  {

    public ExperimentPlayerButtonsPanel(ExperimentController controller) {
        super(new BorderLayout());
        createControlButtons(controller);
        controller.addPropertyChangeListener(ExperimentController.PROPERTY_STATE, this);
        updateButtonState(ExperimentState.READY);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        updateButtonState((ExperimentState)evt.getNewValue());
    }

    private void createControlButtons(ExperimentController controller) {

        _resetBtn = new JButton(new ResetExperimentAction(controller));
        _nextItrBtn = new JButton(new NextIterationAction(controller));
        _runBtn = new JButton(new RunExperimentAction(controller));
        _pauseBtn = new JButton(new PauseExperimentAction(controller));
        _stepBtn = new JButton(new StepExperimentAction(controller));

        JPanel west = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel east = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        west.add(_resetBtn);
        west.add(_nextItrBtn);

        east.add(_stepBtn);
        east.add(_runBtn);
        east.add(_pauseBtn);

        super.add(west, BorderLayout.WEST);
        super.add(east, BorderLayout.EAST);

    }

    public void updateButtonState(ExperimentState state) {
        if (state == ExperimentState.PAUSED || state == ExperimentState.READY) {
            _runBtn.setEnabled(true);
            _pauseBtn.setEnabled(false);
            _stepBtn.setEnabled(true);
            _resetBtn.setEnabled(true);
            _nextItrBtn.setEnabled(true);
        } else if (state == ExperimentState.RUNNING) {
            _runBtn.setEnabled(false);
            _pauseBtn.setEnabled(true);
            _stepBtn.setEnabled(false);
            _resetBtn.setEnabled(false);
            _nextItrBtn.setEnabled(false);
        } else if (state == ExperimentState.COMPLETE) {
            _runBtn.setEnabled(false);
            _pauseBtn.setEnabled(false);
            _stepBtn.setEnabled(false);
            _resetBtn.setEnabled(true);

            _nextItrBtn.setEnabled(false);
        }

    }

    private void enableButton(JButton button, boolean enabled) {
         button.setEnabled(enabled);
         button.setFocusPainted(enabled);
     }



    private JButton _resetBtn;
    private JButton _runBtn;
    private JButton _pauseBtn;
    private JButton _stepBtn;
    private JButton _nextItrBtn;
}
