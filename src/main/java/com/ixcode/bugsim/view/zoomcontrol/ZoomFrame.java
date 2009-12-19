/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.zoomcontrol;

import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.bugsim.view.landscape.action.AgentTypeChoiceCombo;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.swing.JFrameExtension;

/**
 *  Description : Displays the landscape fitted into the screen and then puts a box around it
 * to show you where the main view is zoomed into.
 */
public class ZoomFrame extends JFrameExtension {

    public ZoomFrame(LandscapeView mainView) {
        super("Overview");
        super.getStatusBar().setText("");
          _mainView = mainView;
        _combo = mainView.getAgentTypeChoiceCombo();
        setLandscape(mainView.getLandscape());

    }

    public void setLandscape(Landscape landscape) {

        _zoomView = new LandscapeView(_mainView.getLandscape(), _combo, super.getStatusBar());
        _zoomView.setShowGrid(false);
        super.getContentPane().removeAll();
        super.getContentPane().add(_zoomView);
        super.setGlassPane(new ZoomGlassPane(_mainView, _zoomView));
        super.getGlassPane().setVisible(true);
    }

    public void setCombo(AgentTypeChoiceCombo combo) {
        _combo = combo;
    }


    private LandscapeView _mainView;
    private LandscapeView _zoomView;
    private AgentTypeChoiceCombo _combo;
}
