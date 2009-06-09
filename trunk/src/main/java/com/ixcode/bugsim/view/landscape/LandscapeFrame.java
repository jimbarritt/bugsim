/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.swing.JFrameExtension;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeFrame extends JFrameExtension {
    


    public static final String TITLE = "Landscape Viewer";

    public LandscapeFrame(OpenSimulationAction openSimAction) throws HeadlessException {
        super(TITLE, new JPanel(new BorderLayout()));

        super.setSize(700, 700);
        super.setLocation(400, 0);

        _openSimAction = openSimAction;

        super.setBorderBackground(_view.getBackground());

        super.setSystemExitOnClose(false);
    }

    public LandscapeFrame(Landscape landscape, OpenSimulationAction openSimAction) throws HeadlessException {
        super(TITLE, new JPanel(new BorderLayout()));

        super.setSize(700, 700);
        super.setLocation(400, 0);

        _openSimAction = openSimAction;



        JPanel container = (JPanel)super.getContent();

        _combo = new AgentTypeChoiceCombo(landscape.getSimulation().getAgentRegistry());
        _view = new LandscapeView(landscape, _combo, super.getStatusBar());
        _toolbar = new LandscapeToolbar(_view, _combo, _openSimAction);
        //@todo tidy up the toolbar!!
        container.add(_toolbar, BorderLayout.NORTH);
        container.add(_view, BorderLayout.CENTER);

                super.setBorderBackground(_view.getBackground());
        
//       JComponent glassPane = new OverlayingGlassPane(this);
//        super.setGlassPane(glassPane);
//        getGlassPane().setVisible(true);

        super.setSystemExitOnClose(false);

    }
    public LandscapeView getLandscapeView() {
        return _view;
    }

    public AgentTypeChoiceCombo getAgentChoiceCombo() {
        return _combo;
    }

    public void setLandscape(Landscape landscape) {

        _view.setLandscape(landscape);
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
        return _toolbar;
    }

    private LandscapeView _view;
    private AgentTypeChoiceCombo _combo;
    private OpenSimulationAction _openSimAction;
    private LandscapeToolbar _toolbar;
}
