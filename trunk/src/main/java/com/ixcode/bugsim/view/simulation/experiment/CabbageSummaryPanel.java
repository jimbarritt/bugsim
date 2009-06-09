/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.experiment;

import com.ixcode.bugsim.controller.experiment.IExperiment;
import com.ixcode.bugsim.controller.experiment.IExperimentListener;
import com.ixcode.bugsim.controller.experiment.RandomWalkEdgeEffectExperiment;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.IPropertyValueEditorListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageSummaryPanel extends JPanel implements IExperimentInspectorPanel, PropertyChangeListener, IExperimentListener, IPropertyValueEditor {

    public CabbageSummaryPanel() {
        super(new BorderLayout());

        JPanel content = new JPanel(new GridLayout(4, 4));
        _labels = new EggCountPanel[4][4];
        for (int y = 3; y >= 0; --y) {
            for (int x = 0; x < 4; ++x) {
                _labels[x][y] = new EggCountPanel();
                _labels[x][y].setData(x, y, 0);               
                content.add(_labels[x][y]);
            }

        }
        super.add(content, BorderLayout.CENTER);
    }

    public JComponent getEditingComponent() {
        return this;
    }

    public void reformatValue(String modelValue) {

    }

    public void addPropertyValueEditorListener(IPropertyValueEditorListener l) {

    }

    public void addEditedPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentStarted(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isDisplayOnly() {
        return true;
    }

    public String getPropertyName() {
        return null;
    }

    public JComponent getDisplayComponent() {
        return this;
    }

    public String getValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setValue(String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setReadonly(boolean readonly) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isReadonly() {
        return false;
    }

    public void timeStepExecuted(IExperiment experiment, long timestep) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentComplete(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentStopped(IExperiment experiment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void experimentReset(IExperiment experiment) {
        listenToCabbages();
    }

    public JComponent getViewComponent() {
        return this;
    }

    public void setExperiment(IExperiment experiment) {
        _experiment = (RandomWalkEdgeEffectExperiment)experiment;

        _simulation = _experiment.getSimulation();
        _experiment.addExperimentListener(this);
        listenToCabbages();
    }

    private void listenToCabbages() {
        CabbageAgent[][] cabbages = _experiment.getCabbages();
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                cabbages[x][y].addPropertyChangeListener(this);
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                CabbageAgent[][] cabbages = _experiment.getCabbages();
                for (int x = 0; x < 4; ++x) {
                    for (int y = 0; y < 4; ++y) {
                        _labels[x][y].setData(x, y, cabbages[x][y].getEggCount());
                    }
                    invalidate();
                    repaint();
                }

            }
        });

    }


    private RandomWalkEdgeEffectExperiment _experiment;
    private Simulation _simulation;
    private EggCountPanel[][] _labels;

}
