/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;


import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.swing.property.PropertyGroupPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class IxApplyPropertyChangeAction extends AbstractAction {

    public IxApplyPropertyChangeAction(PropertyGroupPanel panel, ModelBase parameters) {
        super("Apply");
        _panel = panel;
        _parameters = parameters;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        List propertyNames = _parameters.getPropertyNames();
        for (Iterator itr = propertyNames.iterator(); itr.hasNext();) {
            String propertyName = (String)itr.next();
            _parameters.setPropertyValueAsString(propertyName, _panel.getTextValue(propertyName));
        }

    }


    private PropertyGroupPanel _panel;
    private ModelBase _parameters;
}
