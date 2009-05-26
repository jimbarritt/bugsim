/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.framework.experiment.model.ExperimentController;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperimentProgressListener;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.simulation.model.agent.AgentBase;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.StatusBar;
import com.ixcode.framework.util.MemoryStats;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Description : Provides a frame from which you can stop and start the experiment and see where its progress is.
 */
public class ExperimentControllerFrame extends JFrameExtension {



    public ExperimentControllerFrame(ExperimentController controller, JComponent experimentProgressPanel) throws HeadlessException, JavaBeanException {
        super("Experiment Executor [" + controller.getExperiment().getExperimentPlan().getName() + "]");

        JPanel content = new JPanel(new BorderLayout());

        final int BORDER = 5;
        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
        _controllerPanel = new ExperimentControllerPanel(controller, experimentProgressPanel);
        content.add(_controllerPanel, BorderLayout.NORTH);


        _buttonsPanel = new ExperimentPlayerButtonsPanel(controller);
        content.add(_buttonsPanel, BorderLayout.SOUTH);

        super.getContentPane().add(content);

        final StatusBar sb = super.getStatusBar();

        updateStatusText(sb);

        controller.addProgressListener(new IExperimentProgressListener(){
            public void progressNotification(ExperimentProgress progress) {
                updateStatusText(sb);
            }
        }, 1);
    }

    private void updateStatusText(StatusBar sb) {
        String debug = BugsimMain.isDebug() ? "debug" : "";
        sb.setText("  Version " + BugsimMain.getVersion() + " " + debug + "  Memory " + _memStats.getSummary() + " Agents: " + AgentBase.getNumberOfAgentsExtant());
    }


    private ExperimentControllerPanel _controllerPanel;
    private ExperimentPlayerButtonsPanel _buttonsPanel;
    private MemoryStats _memStats = new MemoryStats(Runtime.getRuntime());
    private static final Logger log = Logger.getLogger(ExperimentControllerFrame.class);
}
