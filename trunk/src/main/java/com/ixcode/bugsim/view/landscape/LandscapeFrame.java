/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;

import javax.swing.*;
import static javax.swing.BorderFactory.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeFrame extends JFrameExtension {

    public static final String TITLE = "Landscape Viewer";

    private LandscapeView landscapeView;
    private AgentTypeChoiceCombo agentTypeChoiceCombo;
    private OpenSimulationAction openSimulationAction;
    private LandscapeToolbar landscapeToolbar;

    public LandscapeFrame(Landscape landscape) throws HeadlessException {
        this(landscape, new OpenSimulationAction());
    }

    public LandscapeFrame(OpenSimulationAction openSimAction) throws HeadlessException {
        super(TITLE, new JPanel(new BorderLayout()));

        initSizeAndLocation();

        openSimulationAction = openSimAction;

        super.setBorderBackground(landscapeView.getBackground());

        super.setSystemExitOnClose(false);
    }

    private void initSizeAndLocation() {
        Dimension screenSize = super.getScreenSize();
        super.setSize(new Dimension(screenSize.height, screenSize.height));
        super.setLocation(0, 0);
    }

    public LandscapeFrame(Landscape landscape, OpenSimulationAction openSimAction) throws HeadlessException {
        super(TITLE, new JPanel(new BorderLayout()));

        initSizeAndLocation();        
        
        openSimulationAction = openSimAction;

        JPanel container = (JPanel) super.getContent();

        agentTypeChoiceCombo = new AgentTypeChoiceCombo(landscape.getSimulation().getAgentRegistry());
        landscapeView = new LandscapeView(landscape, agentTypeChoiceCombo, getStatusBar());
        landscapeToolbar = new LandscapeToolbar(landscapeView, agentTypeChoiceCombo, openSimulationAction);

        JPanel landscapeViewContainer = new JPanel(new BorderLayout());
        landscapeViewContainer.setBorder(createEmptyBorder(20, 20, 20, 20));
        landscapeViewContainer.setBackground(Color.DARK_GRAY);
        landscapeViewContainer.add(landscapeView, BorderLayout.CENTER);

        container.add(landscapeToolbar, BorderLayout.NORTH);
        container.add(landscapeViewContainer, BorderLayout.CENTER);

        super.setBorderBackground(landscapeView.getBackground());
        super.setSystemExitOnClose(false);
    }

    public void open() {
        setVisible(true);
    }

    public LandscapeView getLandscapeView() {
        return landscapeView;
    }

    public AgentTypeChoiceCombo getAgentChoiceCombo() {
        return agentTypeChoiceCombo;
    }

    public void setLandscape(Landscape landscape) {

        landscapeView.setLandscape(landscape);
        landscape.fireAgentsChangedEvent();


//        _view.addPropertyChangeListener(new LandscapeStatusBarUpdater(super.getStatusBar()));

    }

    public void addActionToToolbar(Action action) {
        getToolbar().add(action);
    }

    private static JComponent createContent(Landscape landscape) {
        JPanel container = new JPanel(new BorderLayout());
        return container;
    }

    public LandscapeToolbar getToolbar() {
        return landscapeToolbar;
    }


}
