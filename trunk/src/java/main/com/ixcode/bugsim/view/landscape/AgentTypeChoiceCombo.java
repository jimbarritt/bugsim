/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.simulation.model.agent.IAgentInfo;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AgentTypeChoiceCombo extends JComboBox {

    public AgentTypeChoiceCombo(AgentInfoRegistry registry) {
        _registry = registry;
        for (Iterator itr = _registry.getTypeIds().iterator(); itr.hasNext();) {
            String id = (String)itr.next();
            IAgentInfo info = _registry.getInfo(id);
            super.addItem(info.getDisplayName());

        }
        super.setPreferredSize(new Dimension(100, 10));
        super.setMaximumSize(new Dimension(100, 40));

    }

    public IAgentInfo getSelectedInfo() {
        int iAgentInfo = super.getSelectedIndex();
        String selectedId =  (String)_registry.getTypeIds().get(iAgentInfo);
        return _registry.getInfo(selectedId);
    }

    private AgentInfoRegistry _registry;
}
