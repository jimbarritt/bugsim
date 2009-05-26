/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterManipulations;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 8, 2007 @ 3:51:52 PM by jim
 */
public class ManipulationPanel extends JPanel {


    public ManipulationPanel(IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        super(new BorderLayout());

        _lookup = lookup;
        _modelAdapter = modelAdapter;
        initUI();
    }

    private void initUI() {
        JPanel container = new JPanel(new BorderLayout());


        _manipulationSummaryPanel = new ManipulationSummaryPanel(_modelAdapter, _lookup);
        container.add(_manipulationSummaryPanel, BorderLayout.NORTH);

        _manipulationSequencePanel = new ManipulationSequencePanel(_manipulationSummaryPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Sequence", _manipulationSequencePanel);

        container.add(tabbedPane, BorderLayout.CENTER);
//        _btnAdd = new JButton("Add Manipulation");
//        southPanel.add(_btnAdd, BorderLayout.EAST);

//        container.add(southPanel, BorderLayout.SOUTH);
        super.add(container,  BorderLayout.CENTER);

        _manipulationSummaryPanel.addPropertyChangeListener(ManipulationSummaryPanel.P_SELECTED_SEQUENCE, new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
               updateSequencePanel();
            }
        });
    }

    private void updateSequencePanel() {
        _manipulationSequencePanel.setManipulationSequence(_manipulationSummaryPanel.getSelectedSequence());
    }

    public void setParameterManipulations(ParameterManipulations manipulations) {
        _manipulations = manipulations;

        _manipulationSummaryPanel.setParameterManipulations(manipulations);

    }


    private ParameterManipulations _manipulations;


    private JButton _btnAdd;
    private IModelAdapter _modelAdapter;
    private ManipulationSummaryPanel _manipulationSummaryPanel;
    private ManipulationSequencePanel _manipulationSequencePanel;
    private IParameterMapLookup _lookup;
}
