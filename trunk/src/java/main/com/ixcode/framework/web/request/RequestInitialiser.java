/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.request;

import com.ixcode.framework.web.action.ActionHandler;
import com.ixcode.framework.web.action.ActionModel;
import com.ixcode.framework.web.action.IActionInfo;
import com.ixcode.framework.web.session.ISessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: RequestInitialiser.java,v 1.1 2004/09/07 16:10:58 rdjbarri Exp $
 */
public class RequestInitialiser {
    public static void initialiseRequest(HttpServletRequest request, ISessionContext sessionContext) {
        RequestHandler.setModel(request, sessionContext.getModel());
        RequestHandler.setFoldingModel(request, sessionContext.getFoldingModel());
        RequestHandler.setSelectionModel(request, sessionContext.getSelectionModel());
        RequestHandler.setPagedQuery(request, sessionContext.getPagedQuery());
       

        if (sessionContext.getActionModel() != null) {
            ActionModel actionModel = sessionContext.getActionModel();
            IActionInfo actionInfo = ActionHandler.extractActionInfo(request, actionModel);

            RequestHandler.setActionModel(request, actionModel);
            RequestHandler.setActionInfo(request, actionInfo);

        }
    }
}
