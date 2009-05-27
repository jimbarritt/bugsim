/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.action;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : Conatins a set of actions which can then be used to build up a toolbar
 */
public class IxActionModel {

    public void addAction(Action action) {
        _actions.put(action.getValue(Action.NAME), action);
    }

    private Map _actions = new HashMap();
}
