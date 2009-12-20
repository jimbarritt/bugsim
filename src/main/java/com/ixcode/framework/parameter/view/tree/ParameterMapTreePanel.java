/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.view.tree;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.IParameterModel;
import com.ixcode.framework.simulation.experiment.ExperimentTemplateRegistry;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.bugsim.experiment.BugsimExtensionJavaBeanValueFormats;
import com.ixcode.bugsim.experiment.BugsimExperimentTemplateRegistry;
import com.ixcode.bugsim.experiment.experimentX.ExperimentXFactory;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 4, 2007 @ 2:45:43 PM by jim
 */
public class ParameterMapTreePanel extends JPanel {

    public ParameterMapTreePanel(ParameterMap parameterMap) {
        super(new BorderLayout());
        initUI(parameterMap);
    }

    private void initUI(ParameterMap parameterMap) {

        _tree = new JTree(new ParameterMapTreeModel(parameterMap));
        _tree.setCellRenderer(new ParameterModelTreeCellRenderer());
        JScrollPane sp = new JScrollPane(_tree);
        super.add(sp, BorderLayout.CENTER);
    }


    public JTree getTree() {
        return _tree;
    }


    public static void main(String[] args) {
        BugsimExtensionJavaBeanValueFormats.registerBugsimJavaBeanExtensionFormats();
        ExperimentTemplateRegistry.setExperimentTemplateInstance(new BugsimExperimentTemplateRegistry());
        IExperiment experiment = (new ExperimentXFactory()).createExperiment("TrX");
        ExperimentPlan testPlan = experiment.getExperimentPlan();

        final JFrameExtension f = new JFrameExtension("Test Parameter Tree");
        f.getContentPane().setLayout(new BorderLayout());
        ParameterMapTreePanel treePanel = new ParameterMapTreePanel(testPlan.getParameterTemplate());

        treePanel.getTree().getSelectionModel().addTreeSelectionListener(new TreeSelectionListener(){
            public void valueChanged(TreeSelectionEvent e) {
                IParameterModel model = (IParameterModel)e.getPath().getLastPathComponent();
                f.getStatusBar().setText(model.getFullyQualifiedName());
            }
        });

        f.getContentPane().add(treePanel, BorderLayout.CENTER);


        JFrameExtension.centreWindowOnScreen(f);
        f.show();

    }

    private JTree _tree;
}
