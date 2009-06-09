/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.action;

import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * Deals with mapping events to actions from the request.
 *
 * Note that you can only have a single action event per request - they usually
 * map to buttons.
 *
 * @todo consider using a hidden parameter on the form and then javascript to submit the form - but make sure to continue to support the alternative which is where it loops over the request looking for the value.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ActionHandler.java,v 1.1 2004/08/30 11:29:41 rdjbarri Exp $
 */
public class ActionHandler {

    public static IActionInfo extractActionInfo(HttpServletRequest request, ActionModel actionModel) {
        IActionInfo info = null;
        for (Iterator itr = actionModel.getActionInfoBindings().iterator(); itr.hasNext();) {
            ActionInfoBinding binding = (ActionInfoBinding)itr.next();
            IActionInfo candidate= binding.getActionInfo();
            if (RequestHandler.hasParameter(request, candidate.getActionEvent().getEventId())) {
                info = candidate;
                break;
            }
        }
        return info;
    }

    private ActionHandler() {
    }

}
