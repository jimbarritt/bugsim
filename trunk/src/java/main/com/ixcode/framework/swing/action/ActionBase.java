/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.action;

import com.ixcode.framework.swing.IxImageManipulation;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public abstract class ActionBase extends AbstractAction {
    protected ActionBase(String name, String iconResourcePath) {
        this(name, IxImageManipulation.createImageIcon(iconResourcePath));
        super.putValue(Action.SHORT_DESCRIPTION, name);
    }

    protected ActionBase(String name) {
        super(name);
    }

    protected ActionBase(String name, Icon icon) {
        super(name, icon);
    }




}
