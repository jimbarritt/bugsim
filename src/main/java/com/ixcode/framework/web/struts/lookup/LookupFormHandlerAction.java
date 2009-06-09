/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.struts.lookup;

import com.ixcode.framework.model.lookup.Lookup;
import com.ixcode.framework.model.lookup.LookupHandler;
import com.ixcode.framework.web.servlet.ServletExceptionWrapper;
import com.ixcode.framework.web.session.SessionHandler;
import com.ixcode.framework.web.struts.StrutsActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Handles the lookup_results form to deal with paged queries and when the user selects something.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: LookupFormHandlerAction.java,v 1.2 2004/09/17 10:58:05 rdjbarri Exp $
 */
public class LookupFormHandlerAction extends StrutsActionBase {

    protected ActionForward execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LookupSessionContext sessionContext = (LookupSessionContext)SessionHandler.getSessionContext(request.getSession(), LookupSessionContext.class);

        handlePagingEvents(request, sessionContext);

        //@todo this is where you want to check the request for any sorting / filtering parameters and update the paged query with new IServerQueryParameters accordingly.
//        sessionContext.getPagedQuery().setQueryParameters(...);

        sessionContext.setModel(sessionContext.getPagedQuery().getCurrentDisplayPage());

        super.initialiseRequest(request,sessionContext);
        int lookupId = LookupHandler.getLookupIdParameter(request);
        Lookup lookup = LookupHandler.getLookupContext(request.getSession()).getLookup(lookupId);

        String forward = LookupForwards.LOOKUP_RESULTS;

        // @todo check for cancel button - need to create a Cancel Action - dont use the default handleActions in the base class.
//        LookupHandler.setLookupCancelled(request);
        // else ...
        if (LookupHandler.isLookupSubmission(request)) {
            String selectedItemXPath = LookupHandler.getSelectedItemParameter(request);
            try {
                LookupHandler.handleLookupSubmission(request, (List)sessionContext.getModel(), selectedItemXPath, lookup);
            } catch (IOException e) {
                throw new ServletExceptionWrapper(e);
            }
        }


        return super.findForward(actionMapping, forward);
    }
}
