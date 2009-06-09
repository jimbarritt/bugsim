/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

/**
 * Subclass this to provide extra information specific to your controller technology - e.g.
 * Struts uses a forward name which is then looked up by the struts framework.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: ActionInfoBase.java,v 1.2 2004/08/31 02:25:01 rdjbarri Exp $
 */
public abstract class ActionInfoBase implements IActionInfo{

    public ActionInfoBase(ActionEvent actionEvent) {
        _actionEvent = actionEvent;        
    }

    public ActionEvent getActionEvent() {
        return _actionEvent;
    }


    public String toString() {
        return "ActionInfo: event=" + _actionEvent;
    }

    private ActionEvent _actionEvent;
}
