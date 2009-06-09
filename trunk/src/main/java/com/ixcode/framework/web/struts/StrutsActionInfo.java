/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.struts;

import com.ixcode.framework.web.action.ActionEvent;
import com.ixcode.framework.web.action.ActionInfoBase;


/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: StrutsActionInfo.java,v 1.1 2004/08/30 11:29:46 rdjbarri Exp $
 */
public class StrutsActionInfo extends ActionInfoBase {

    public StrutsActionInfo(String forwardName) {
        this(forwardName, null);
    }

    public StrutsActionInfo(String forwardName, ActionEvent actionEvent) {
        super(actionEvent);
        _forwardName = forwardName;

    }

    public String getForwardName() {
        return _forwardName;
    }

    public String toString() {
        return _forwardName;
    }



    private String _forwardName;


}
