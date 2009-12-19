/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.action;

import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.bugsim.view.zoomcontrol.*;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeToolbar extends JToolBar {

    public LandscapeToolbar(LandscapeView view, AgentTypeChoiceCombo combo, OpenSimulationAction openSimAction) {
        super.add(new ShowLandscapePropertiesAction(view));
        super.add(new OpenBackgroundImageAction(view));

        super.add(combo);

//        Action addAgent = new AddAgentAction(view, combo);
//        JToggleButton toggleAgent = new JToggleButton(addAgent);
//        toggleAgent.setText("");
//        super.add(toggleAgent);
        super.add(new SaveLandscapeImageAction(view));
//        super.add(openSimAction);
//        super.add(new LoadExperimentalSetupAction(view));
        super.add(new TestIntersectionAction(view));
        super.add(new ZoomControlCombo(view));
        super.add(new CenterOnNextAgentAction(view));
        super.add(new InspectButterflyAgentAction(view));

    }

    private LandscapeView _view;
}
