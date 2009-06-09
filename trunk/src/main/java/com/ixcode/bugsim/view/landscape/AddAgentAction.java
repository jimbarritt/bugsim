/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.swing.action.ActionBase;

import java.awt.event.ActionEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AddAgentAction extends ActionBase {

    public AddAgentAction(LandscapeView view, AgentTypeChoiceCombo combo) {
        super("Add Agent", "/icons/add.gif");
        _view = view;
        _combo = combo;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (!_view.isViewMode(LandscapeViewMode.ADD_AGENT)) {
            _view.setViewMode(LandscapeViewMode.ADD_AGENT);
            
        } else {
            _view.setViewMode(LandscapeViewMode.DISPLAY);
        }
    }

    private LandscapeView _view;
    private AgentTypeChoiceCombo _combo;
}
