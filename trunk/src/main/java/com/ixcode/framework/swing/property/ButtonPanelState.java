/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButtonPanelState extends TypeSafeEnum  {

    public static final ButtonPanelState CLEAN = new ButtonPanelState("clean");
    public static final ButtonPanelState DIRTY = new ButtonPanelState("dirty");

    protected ButtonPanelState(String name) {
        super(name);
    }
}
