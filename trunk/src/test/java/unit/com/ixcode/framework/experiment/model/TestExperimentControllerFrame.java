/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.PropertyGroupPanel;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class TestExperimentControllerFrame extends JFrameExtension {

    public TestExperimentControllerFrame(ExperimentController experimentController) throws HeadlessException {
        super("Test Experiment Control");

        createContent(experimentController);

    }

    private void createContent(ExperimentController experimentController) {
        JPanel p = new JPanel();

        p.setLayout(new BorderLayout());
        JPanel status = createStatus(experimentController);
        p.add(status, BorderLayout.CENTER);
        JPanel buttons = createButtons(experimentController);
        p.add(buttons, BorderLayout.SOUTH);
        super.getContentPane().add(p);
    }

    private JPanel createStatus(final ExperimentController experimentController) {
        PropertyGroupPanel p = new PropertyGroupPanel("Experiment Properties");
        final ReadOnlyPropertyEditor state = new ReadOnlyPropertyEditor("state", "State", 20);
        p.addPropertyEditor(state);
        state.setValue(experimentController.getExperimentState().getName());
        experimentController.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ExperimentController.PROPERTY_STATE)) {
                    state.setValue(((ExperimentState)evt.getNewValue()).getName());
                }
            }
        });

        final ReadOnlyPropertyEditor maxTimesteps = new ReadOnlyPropertyEditor("maxTS", "Max Timesteps", 20);
        p.addPropertyEditor(maxTimesteps);
        maxTimesteps.setValue("" + experimentController.getMaxStepsToExecute());
        experimentController.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ExperimentController.PROPERTY_MAX_TIMESTEPS)) {
                    maxTimesteps.setValue(((ExperimentState)evt.getNewValue()).getName());
                }
            }
        });

        final ReadOnlyPropertyEditor timesteps = new ReadOnlyPropertyEditor("timesteps", "Timesteps executed", 20);
        p.addPropertyEditor(timesteps);
        final ReadOnlyPropertyEditor timestepsLR = new ReadOnlyPropertyEditor("timestepsLastReset", "Timestep since last reset", 20);
        p.addPropertyEditor(timestepsLR);
        final ReadOnlyPropertyEditor currentItr = new ReadOnlyPropertyEditor("currentItr", "Current Iteration", 20);
        p.addPropertyEditor(currentItr);
        final ReadOnlyPropertyEditor currentRep = new ReadOnlyPropertyEditor("currentRep", "Current Replicant", 20);
        p.addPropertyEditor(currentRep);
        final ReadOnlyPropertyEditor itrTimesteps = new ReadOnlyPropertyEditor("itrTimesteps", "Iteration Timesteps", 20);
        p.addPropertyEditor(itrTimesteps);
        final ReadOnlyPropertyEditor elapsedTime = new ReadOnlyPropertyEditor("elapsedTime", "Elapsed Time", 20);
        p.addPropertyEditor(elapsedTime);


        experimentController.addProgressListener(new IExperimentProgressListener() {
            public void progressNotification(ExperimentProgress progress) {
                timesteps.setValue("" + progress.getExecutedTimesteps());
                timestepsLR.setValue("" + progress.getTimestepsSinceLastReset());
                currentItr.setValue("" + progress.getCurrentIterationFormatted());
                currentRep.setValue("" + progress.getCurrentReplicateFormatted());
                itrTimesteps.setValue("" + progress.getIterationTimestepsExecuted());
                elapsedTime.setValue(progress.getElapsedTimeFormatted());
            }

            public void iterationComplete() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void experimentComplete() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        }, 1);

        return p;

    }

    private JPanel createButtons(final ExperimentController experimentController) {
        JPanel p = new JPanel();

        _run = new JButton("Run");
        _pause = new JButton("Pause");
        _step = new JButton("Step");
        _reset = new JButton("Reset");

        _run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experimentController.run();
            }
        });

        _pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experimentController.pause();
            }
        });

        _step.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experimentController.step();
            }
        });

        _reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experimentController.reset();
            }
        });

        experimentController.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ExperimentController.PROPERTY_STATE)) {
                    updateButtonState(((ExperimentState)evt.getNewValue()));
                }
            }
        });
        updateButtonState(experimentController.getExperimentState());


        p.add(_run);
        p.add(_pause);
        p.add(_step);
        p.add(_reset);

        return p;
    }

    private void updateButtonState(ExperimentState state) {
        if (state == ExperimentState.PAUSED || state == ExperimentState.READY) {
            _run.setEnabled(true);
            _pause.setEnabled(false);
            _step.setEnabled(true);
            _reset.setEnabled(true);
        } else if (state == ExperimentState.RUNNING) {
            _run.setEnabled(false);
            _pause.setEnabled(true);
            _step.setEnabled(false);
            _reset.setEnabled(false);
        } else if (state == ExperimentState.COMPLETE) {
            _run.setEnabled(false);
            _pause.setEnabled(false);
            _step.setEnabled(false);
            _reset.setEnabled(true);
        }
    }

    JButton _run;
    JButton _pause;
    JButton _step;
    JButton _reset;


}
