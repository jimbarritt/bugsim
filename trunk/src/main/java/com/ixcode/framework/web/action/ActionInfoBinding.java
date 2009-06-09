/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

/**
 * Binds the actionInfos to tell us wether they are enabled or hidden.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ActionInfoBinding.java,v 1.1 2004/08/30 11:29:41 rdjbarri Exp $
 */
public class ActionInfoBinding {

    public ActionInfoBinding(IActionInfo info) {
        _actionInfo = info;
    }


    public boolean isVisible() {
        return _isVisible;
    }

    public void setVisible(boolean visible) {
        _isVisible = visible;
    }

    public boolean isEnabled() {
        return _isEnabled;
    }

    public void setEnabled(boolean enabled) {
        _isEnabled = enabled;
    }

    public IActionInfo getActionInfo() {
        return _actionInfo;
    }

    private boolean _isVisible = true;
    private boolean _isEnabled = true;

    private IActionInfo _actionInfo;
}
