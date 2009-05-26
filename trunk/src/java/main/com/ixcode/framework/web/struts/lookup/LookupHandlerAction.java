/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.struts.lookup;

import com.ixcode.framework.model.lookup.Lookup;
import com.ixcode.framework.model.lookup.LookupContext;
import com.ixcode.framework.model.lookup.LookupHandler;
import com.ixcode.framework.model.query.IPagedQuery;
import com.ixcode.framework.web.session.SessionHandler;
import com.ixcode.framework.web.struts.StrutsActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is what you put in the URL of the <lookupSource /> tag.
 *
 * It instanitiates the query, goes to the first server page and then forwards to the view.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: LookupHandlerAction.java,v 1.1 2004/09/13 11:10:33 rdjbarri Exp $
 */
public class LookupHandlerAction extends StrutsActionBase {

    protected ActionForward execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LookupSessionContext sessionContext = new LookupSessionContext();
        SessionHandler.setSessionContext(request.getSession(), sessionContext);

        LookupContext lookupContext = LookupHandler.getLookupContext(request.getSession());

        if (LookupHandler.isLookupInvokedRequest(request)) {
            lookupContext.resetPagedQuery(); // clean up if the user just clicked the close button.
        }

        int id = LookupHandler.getLookupIdParameter(request);
        Lookup lookup = lookupContext.getLookup(id);
        IPagedQuery pagedQuery = LookupHandler.createPagedQuery(lookup, 30, 20);        
        pagedQuery.firstServerPage();

        sessionContext.setPagedQuery(pagedQuery);
        sessionContext.setModel(pagedQuery.getCurrentDisplayPage());

        super.initialiseRequest(request,sessionContext);

        


        return super.findForward(actionMapping, LookupForwards.LOOKUP_RESULTS);
    }


}
