/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: PagedQueryHandler.java,v 1.3 2004/08/30 11:29:44 rdjbarri Exp $
 */
public class PagedQueryHandler {

    /**
     * Interprets the requests parameters and does the right thing to the query.
     */
    public static boolean handlePagingEvents(HttpServletRequest request, IPagedQuery query) throws ServletException, EndOfResultsException {
        String pagingCommandParam = request.getParameter(PARAM_PAGING_COMMAND);
        if (pagingCommandParam  == null || pagingCommandParam.length() ==0) {
            return false;
        }
        PagedQueryEvents command = PagedQueryEvents.parseString(request.getParameter(PARAM_PAGING_COMMAND));

        if (command == PagedQueryEvents.NEXT_SERVER_PAGE) {
            query.nextServerPage();
        } else if (command == PagedQueryEvents.PREVIOUS_SERVER_PAGE) {
            query.previousServerPage();
        } else if (command == PagedQueryEvents.LAST_SERVER_PAGE) {
            query.lastServerPage();
        } else if (command == PagedQueryEvents.FIRST_SERVER_PAGE) {
            query.firstServerPage();
        } else if (command == PagedQueryEvents.GOTO_DISPLAY_PAGE) {
            int pageNumber = Integer.parseInt(RequestHandler.getParameter(request, PARAM_DISPLAY_PAGE));
            query.gotoDisplayPage(pageNumber);
        } else {
            throw new IllegalStateException("Unhandled command in request at '" + PARAM_PAGING_COMMAND + "' - " + command);
        }
        return true;
    }

    public static boolean containsPagingEvents(HttpServletRequest request) {
        return request.getParameter(PARAM_PAGING_COMMAND) != null;
    }

    public static final String PARAM_PAGING_COMMAND = "paging-command";
    public static final String PARAM_DISPLAY_PAGE = "display-page";


}
