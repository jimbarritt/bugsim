/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a list of all the ActionInfoBase's which are associated with this request
 * and allows you to ask wether they are hidden or enabled.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: ActionModel.java,v 1.2 2004/08/31 06:42:30 rdjbarri Exp $
 */
public class ActionModel {

    public ActionModel() {
    }

    public void addActionInfo(IActionInfo actionInfo) {
        ActionInfoBinding binding = new ActionInfoBinding(actionInfo);
        _actions.add(binding);
        _eventsToAction.put(actionInfo.getActionEvent().getEventId(), binding);
    }

    public List getActionInfoBindings() {
        return _actions;
    }

    public ActionInfoBinding getActionInfoBinding(ActionEvent event) {
        return getActionInfoBinding(event.getEventId());
    }

    public ActionInfoBinding getActionInfoBinding(String actionEventId) {
        if (!_eventsToAction.containsKey(actionEventId)) {
            throw new IllegalStateException("Could not find event with id '" + actionEventId + "'");
        }
        return (ActionInfoBinding)_eventsToAction.get(actionEventId);
    }

    private List _actions = new ArrayList();
    private Map _eventsToAction = new HashMap();
}
